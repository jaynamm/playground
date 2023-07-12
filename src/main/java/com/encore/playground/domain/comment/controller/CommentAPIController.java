package com.encore.playground.domain.comment.controller;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.service.CommentService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
@Tag(name="Comment", description = "댓글 기능 관련 API")
public class CommentAPIController {
    private final CommentService commentService;

    /**
     * 피드 글번호를 이용하여 해당 글에 달린 댓글들 가져오기
     *
     * @param feedId 피드 글번호
     * @return 해당 글에 달린 댓글들 List
     */
    @Operation(summary = "댓글 목록 가져오기 (피드 상세화면용)", description = "피드 글번호를 입력하면 해당 글에 달린 댓글들을 모두 가져온다.")
    @Parameter(name="feedId", description="피드 글번호", example="1", required = true)
    @GetMapping("/list/{feedId}")
    public Slice<CommentListDto> getCommentsInFeed(@PathVariable Long feedId, HttpServletRequest request, @PageableDefault(size=10) Pageable pageable) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            return commentService.getCommentsInFeed(CommentReadDto.builder().feedId(feedId).build(), memberIdDto, pageable);
        } else
            return null;
    }

    /**
     * 댓글 작성
     * @param commentWriteDto 해당 프로퍼티를 가진 Dto<br>
     *                   feedId: 피드 글번호<br>
     *                   memberId: 유저 id<br>
     *                   content: 댓글 내용
     */
    @Operation(summary = "댓글 작성", description = "피드 글 번호에 해당하는 피드에 댓글을 작성한다.<br>axios의 data란에 아래 예시(Request Body)와 같이 피드 번호, 작성자, 댓글 내용을 입력해야 한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 작성에 필요한 정보<br>feedId: 댓글을 적을 피드 글번호<br>memberId: <br>content: 댓글 내용",
            required = true,
            content = @Content(schema = @Schema(implementation = CommentWriteDto.class))
    )
    @PostMapping(value = "/write")
    public void write(@RequestBody CommentWriteDto commentWriteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            commentService.writeComment(commentWriteDto, memberIdDto);
        }
    }

    /**
     * 댓글 수정
     * @param commentModifyDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     *                   content: 수정할 댓글 내용
     */
    @Operation(summary = "댓글 수정", description = "댓글 테이블 id에 해당하는 댓글을 수정한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 수정에 필요한 정보<br>id: 댓글 테이블 id<br>content: 수정할 댓글 내용",
            required = true,
            content = @Content(schema = @Schema(implementation = CommentModifyDto.class))
    )
    @PostMapping(value = "/modify")
    public ResponseEntity<?> modify(@RequestBody CommentModifyDto commentModifyDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (commentService.isCommentWriter(commentModifyDto.getId(), memberIdDto)) {
                commentService.modifyComment(commentModifyDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.COMMENT_MODIFY
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.COMMENT_MODIFY_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }

    /**
     * 댓글 삭제
     * @param commentDeleteDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     */
    @Operation(summary = "댓글 삭제", description = "댓글 테이블 id에 해당하는 댓글을 삭제한다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 삭제에 필요한 댓글 번호<br>id: 삭제할 댓글 테이블 id",
            required = true,
            content = @Content(schema = @Schema(implementation = CommentDeleteDto.class))
    )
    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody CommentDeleteDto commentDeleteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (commentService.isCommentWriter(commentDeleteDto.getId(), memberIdDto)) {
                commentService.deleteComment(commentDeleteDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.COMMENT_DELETE
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.COMMENT_DELETE_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }
}
