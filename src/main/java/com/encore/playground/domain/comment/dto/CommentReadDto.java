package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReadDto {
    private long feedid;
    private String userid;
}
