package com.encore.playground.domain.feed.controller;

import com.encore.playground.domain.comment.dto.CommentReadDto;
import com.encore.playground.domain.comment.service.CommentService;
import com.encore.playground.domain.feed.dto.*;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RequestMapping("/api/feed")
@RestController
//@CrossOrigin(originPatterns = "http://localhost:*")
@Tag(name="Feed", description = "피드 기능 관련 API")
public class FeedAPIController {
    private final FeedService feedService;
    private final CommentService commentService;

    /**
     * 현재 DB에 저장된 모든 피드를 반환하는 메소드
     * @return JSON 형태의 피드 리스트
     */
    @Operation(summary = "피드 메인페이지", description = "메인 페이지 접속 시 피드 목록을 반환한다.")
    @GetMapping(value = "/list")
    public Slice<FeedListDto> feedMain(HttpServletRequest request, @PageableDefault(size=10) Pageable pageable) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            return feedService.feedPage(memberIdDto, pageable);
        } else
            return null;
    }

    /**
     * 클릭한 피드의 상세 내용 및 그 피드의 댓글 목록을 반환하는 메소드
     * @param id: 상세보기 할 피드의 글 번호
     * @return JSON 객체: {
     *     feed: 피드 상세 내용,
     *     comments: 해당 피드에 달린 댓글 목록(Array)
     * }
     */
    @Operation(summary = "피드 상세보기", description = "클릭한 피드의 상세 내용 및 그 피드의 댓글 목록을 반환한다.")
    @Parameter(name = "id", description = "피드 글 번호", example = "1", required = true)
    @GetMapping(value = "/view/{id}")
    public ResponseEntity<?> getFeed(@PathVariable Long id, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            HashMap<String, Object> feedAndComments = new HashMap<>();
            FeedListDto feedListDto = feedService.getFeed(id, memberIdDto);
        feedAndComments.put("feed", feedListDto);
        feedAndComments.put("comments", commentService.getCommentsInFeed(
                        CommentReadDto.builder().feedId(id).build(),
                        memberIdDto,
                        Pageable.ofSize(10)
                    )
            );
            String responseMessage = "";
            if (feedService.isFeedWriter(feedListDto.getId(), memberIdDto)) {
                responseMessage = ResponseMessage.FEED_DETAIL_SUCCESS;
            } else {
                responseMessage = ResponseMessage.FEED_DETAIL_FAILED;
            }
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.OK,
                            responseMessage,
                            feedAndComments
                    ),
                    HttpStatus.OK
            );
        } else
            return null;
    }

    @GetMapping(value = "/test")
    public Slice<FeedListDto> getFeedTest(@PageableDefault(size=10) Pageable pageable){
        System.out.println(pageable);
        // 첫 페이지가 들어왔을 경우 0번 페이지
        // 이후 페이지를 보낸 적이 있을 경우 pageable.next()를 통해 다음 페이지로 이동

        return feedService.getFeedTest(pageable);
    }

    /**
     * 피드 글을 작성하는 메소드
     * @param feedWriteDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * memberId: 작성자 아이디<br>
     * content: 작성한 피드 내용<br>
     * @return 작성한 글을 추가한 JSON 형태의 피드 리스트
     */
    // TODO: jwt로만 멤버 정보를 받을 경우 swagger에서는 테스트 불가능
    @Operation(summary = "피드 작성", description = "로그인 한 사용자 계정을 작성자로 하여 글 내용을 받아 피드를 작성한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "피드 작성 정보<br>content: 피드 글 내용",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedWriteDto.class)
            )
    )
    @PostMapping(value = "/write")
    public void write(@RequestBody FeedWriteDto feedWriteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            feedService.write(feedWriteDto, memberIdDto);
        }
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 수정하는 메소드
     * @param feedModifyDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * id: 수정할 feedModifyDto 글 번호<br>
     * content: 수정할 피드 글 내용
     * @return 글 수정사항을 반영한 JSON 형태의 피드 리스트
     */
    @Operation(summary = "피드 수정", description = "상세보기 중인 피드의 id와 수정할 글 내용인 content를 받아서 해당 피드의 내용을 수정한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "피드 수정 정보<br>id: 피드 테이블 id<br>content: 수정할 피드 글 내용",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedModifyDto.class)
            )
    )
    @PostMapping(value = "/modify")
    public ResponseEntity<?> modify(@RequestBody FeedModifyDto feedModifyDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (feedService.isFeedWriter(feedModifyDto.getId(), memberIdDto)) {
                feedService.modify(feedModifyDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.FEED_MODIFY_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.FEED_MODIFY_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }

    /**
     * 피드 글 번호(PK)를 통해 피드 글을 삭제하는 메소드
     * @param feedDeleteDto 다음의 프로퍼티를 포함한 JSON 입력<br>
     * id: 삭제할 피드 글 번호
     * @return 글 삭제를 반영한 JSON 형태의 피드 리스트
     */
    @Operation(summary = "피드 삭제", description = "피드 id를 받아 그 id에 해당하는 피드를 삭제한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "삭제할 피드의 정보<br>id: 피드 테이블 id",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedDeleteDto.class)
            )
    )
    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FeedDeleteDto feedDeleteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (feedService.isFeedWriter(feedDeleteDto.getId(), memberIdDto)) {
                feedService.delete(feedDeleteDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.FEED_DELETE_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.FEED_DELETE_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }
}
