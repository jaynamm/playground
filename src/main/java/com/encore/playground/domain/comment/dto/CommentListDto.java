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
    private long commentid;
    private long feedNo;
    private String userid;
    private int commentNo;
    private LocalDateTime uploadTime;
    private int likeCount;
    private String article;

    public CommentListDto(Comment entity) {
        this.commentid = entity.getCommentid();
        this.feedNo = entity.getFeed().getFeedNo();
        this.userid = entity.getUserid();
        this.commentNo = entity.getCommentNo();
        this.uploadTime = entity.getUploadTime();
        this.likeCount = entity.getLikeCount();
        this.article = entity.getArticle();
    }
}
