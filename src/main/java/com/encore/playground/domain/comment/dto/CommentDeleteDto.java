package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteDto {
    private long feedid;
    private long id;

    public CommentDeleteDto(Comment entity) {
        this.feedid = entity.getFeed().getId();
        this.id = entity.getId();
    }
}
