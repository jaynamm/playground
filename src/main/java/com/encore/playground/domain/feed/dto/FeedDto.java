package com.encore.playground.domain.feed.dto;

import com.encore.playground.domain.feed.entity.Feed;
import lombok.*;

import java.time.LocalDateTime;

@Data // 모든 필드의 getter, setter, toString, equals, hashCode 메소드를 자동으로 생성
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDto {
    private long id;
    private String memberId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int likeCount;
    private int commentCount;
    private int commentTotalCount;
    private int viewCount;
    private String content;

    public Feed toEntity() { // FeedDto를 Feed 엔티티로 변환
        return Feed.builder()
                .id(id)
                .memberId(memberId)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .commentTotalCount(commentTotalCount)
                .viewCount(viewCount)
                .content(content)
                .build();
    }

    public FeedDto(Feed entity) { // Feed 엔티티를 FeedDto로 변환
        this.id = entity.getId();
        this.memberId = entity.getMemberId();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.likeCount = entity.getLikeCount();
        this.commentCount = entity.getCommentCount();
        this.commentTotalCount = entity.getCommentTotalCount();
        this.viewCount = entity.getViewCount();
        this.content = entity.getContent();
    }
}
