package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListDto {
    private long id;
    private long feedId;
    private String memberId;
//    private int commentNo;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int likeCount;
    private String content;

    public CommentListDto(Comment entity) {
        this.id = entity.getId();
        this.feedId = entity.getFeed().getId();
        this.memberId = entity.getMemberId();
//        this.commentNo = entity.getCommentNo();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.likeCount = entity.getLikeCount();
        this.content = entity.getContent();
    }
}
