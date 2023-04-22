package com.encore.playground.domain.feed.dto;

import com.encore.playground.domain.feed.entity.Feed;
import lombok.*;

import java.time.LocalDateTime;

@Data // 모든 필드의 getter, setter, toString, equals, hashCode 메소드를 자동으로 생성
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDto {
    private long feedNo;
    private String userid;
    private LocalDateTime uploadTime;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private String article;

    public Feed toEntity() { // FeedDto를 Feed 엔티티로 변환
        return Feed.builder()
                .feedNo(feedNo)
                .userid(userid)
                .uploadTime(uploadTime)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .viewCount(viewCount)
                .article(article)
                .build();
    }

    public FeedDto(Feed entity) { // Feed 엔티티를 FeedDto로 변환
        this.feedNo = entity.getFeedNo();
        this.userid = entity.getUserid();
        this.uploadTime = entity.getUploadTime();
        this.likeCount = entity.getLikeCount();
        this.commentCount = entity.getCommentCount();
        this.viewCount = entity.getViewCount();
        this.article = entity.getArticle();
    }
}
