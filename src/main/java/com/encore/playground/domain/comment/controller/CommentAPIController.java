package com.encore.playground.domain.comment.controller;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "피드 상세화면용", description = "피드 글번호를 입력하면 해당 글에 달린 댓글들을 모두 가져온다.")
    @Parameter(name="feedId", description="피드 글번호", example="1", required = true)
    @GetMapping("/list/{feedId}")
    public List<CommentListDto> getCommentsInFeed(@PathVariable Long feedId) {
        return commentService.getCommentsInFeed(CommentReadDto.builder().feedId(feedId).build());
    }

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param memberId 유저 id
     * @return 해당 유저가 작성한 댓글 목록
     */
    @Operation(summary = "마이페이지용", description = "회원의 id를 입력하면 해당 사용자가 작성한 댓글들을 모두 가져온다.")
    @Parameter(name="memberId", description="댓글 작성자의 memberId", example="작성자", required = true)
    @GetMapping("/list/user/{memberId}")
    public List<CommentListDto> getCommentsByUser(@PathVariable String memberId) {
        return commentService.getCommentsByUser(CommentReadDto.builder().memberId(memberId).build());
    }

    /**
     * 댓글 작성
     * @param commentWriteDto 해당 프로퍼티를 가진 Dto<br>
     *                   feedId: 피드 글번호<br>
     *                   memberId: 유저 id<br>
     *                   content: 댓글 내용
     */
    @Operation(summary = "댓글 작성", description = "피드 글 번호에 해당하는 피드에 댓글을 작성한다.<br>피드 번호는 URL에, 작성자와 댓글 내용은 RequestBody에 담아서 보낸다.")
    @Parameter(name="feedId", description="피드 글번호", example="1", required = true)
    @PostMapping(value = "/write/{feedId}")
    public void write(@PathVariable long feedId, @RequestBody CommentWriteDto commentWriteDto) {
        commentService.writeComment(feedId, commentWriteDto);
    }

    /**
     * 댓글 수정
     * @param commentModifyDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     *                   content: 수정할 댓글 내용
     */
    @Operation(summary = "댓글 수정", description = "댓글 테이블 id에 해당하는 댓글을 수정한다.")
    @Parameters({
            @Parameter(name="id", description="댓글 테이블 id", example="1", required = true),
            @Parameter(name="content", description="수정할 댓글 내용", example="수정된 댓글 내용", required = true)
    })
    @PostMapping(value = "/modify")
    public void modify(@RequestBody CommentModifyDto commentModifyDto) {
        commentService.modifyComment(commentModifyDto);
    }

    /**
     * 댓글 삭제
     * @param commentDeleteDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     */
    @Operation(summary = "댓글 삭제", description = "댓글 테이블 id에 해당하는 댓글을 삭제한다.")
    @Parameter(name="id", description="댓글 테이블 id", example="1", required = true)
    @DeleteMapping(value = "/delete")
    public void delete(@RequestBody CommentDeleteDto commentDeleteDto) {
        commentService.deleteComment(commentDeleteDto);
    }
}
