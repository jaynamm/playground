package com.encore.playground.domain.comment.controller;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentAPIController {
    private final CommentService commentService;

    /**
     * 피드 글번호를 이용하여 해당 글에 달린 댓글들 가져오기
     *
     * @param feedId 피드 글번호
     * @return 해당 글에 달린 댓글들 List
     */
//    @RequestMapping(value = "/getcommentsinfeed")
    @GetMapping("/list/{feedId}")
    public List<CommentListDto> getCommentsInFeed(@PathVariable Long feedId
//            , @RequestBody CommentReadDto commentReadDto
    )
    {
        return commentService.getCommentsInFeed(CommentReadDto.builder().feedId(feedId).build());
    }

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param memberId 유저 id
     * @return 해당 유저가 작성한 댓글 목록
     */
//    @RequestMapping(value = "/getcommentsbyuser")
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
    @RequestMapping(value = "/write")
    public void write(@RequestBody CommentWriteDto commentWriteDto) {
        commentService.writeComment(commentWriteDto);
    }

    /**
     * 댓글 수정
     * @param commentModifyDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     *                   content: 수정할 댓글 내용
     */
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody CommentModifyDto commentModifyDto) {
        commentService.modifyComment(commentModifyDto);
    }

    /**
     * 댓글 삭제
     * @param commentDeleteDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 테이블 id<br>
     */
    @RequestMapping(value = "/delete")
    public void delete(@RequestBody CommentDeleteDto commentDeleteDto) {
        commentService.deleteComment(commentDeleteDto);
    }
}
