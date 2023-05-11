package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import com.encore.playground.domain.feed.entity.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private long commentid;
    private Feed feed;
    private String userid;
    private int commentNo;
    private LocalDateTime uploadTime;
    private int likeCount;
    private String article;

    public Comment toEntity() {
        return Comment.builder()
                .commentid(commentid)
                .feed(feed)
                .userid(userid)
                .commentNo(commentNo)
                .uploadTime(uploadTime)
                .likeCount(likeCount)
                .article(article)
                .build();
    }

    public CommentDto(Comment entity) {
        this.commentid = entity.getCommentid();
        this.feed = entity.getFeed();
        this.userid = entity.getUserid();
        this.commentNo = entity.getCommentNo();
        this.uploadTime = entity.getUploadTime();
        this.likeCount = entity.getLikeCount();
        this.article = entity.getArticle();
    }
}
