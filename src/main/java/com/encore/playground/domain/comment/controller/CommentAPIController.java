package com.encore.playground.domain.comment.controller;

import com.encore.playground.domain.comment.dto.CommentDto;
import com.encore.playground.domain.comment.dto.CommentListDto;
import com.encore.playground.domain.comment.dto.CommentReadDto;
import com.encore.playground.domain.comment.dto.CommentWriteDto;
import com.encore.playground.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentAPIController {
    private final CommentService commentService;

    @RequestMapping(value = "/getcommentsinfeed")
    public List<CommentListDto> getCommentsInFeed(@RequestBody CommentReadDto commentDto) {
        return commentService.getCommentsInFeed(commentDto);
    }

    @RequestMapping(value = "/getcommentsbyuser")
    public List<CommentListDto> getCommentsByUser(@RequestBody CommentReadDto commentDto) {
        return commentService.getCommentsByUser(commentDto);
    }

    @RequestMapping(value = "/write")
    public void write(@RequestBody CommentWriteDto commentDto) {
        commentService.writeComment(commentDto);
    }

    @RequestMapping(value = "/modify")
    public void modify(@RequestBody CommentDto commentDto) {
        commentService.modifyComment(commentDto);
    }

    @RequestMapping(value = "/delete")
    public void delete(@RequestBody CommentDto commentDto) {
        commentService.deleteComment(commentDto);
    }
}
