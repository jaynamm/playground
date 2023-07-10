package com.encore.playground.domain.feed.dto;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data // 모든 필드의 getter, setter, toString, equals, hashCode 메소드를 자동으로 생성
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDto {
    private Long id;
    private Member member;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer commentCount;
    private Integer viewCount;
    private String content;

    public Feed toEntity() { // FeedDto를 Feed 엔티티로 변환
        return Feed.builder()
                .id(id)
                .member(member)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .viewCount(viewCount)
                .content(content)
                .build();
    }

    public FeedDto(Feed entity) { // Feed 엔티티를 FeedDto로 변환
        this.id = entity.getId();
        this.member = entity.getMember();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.viewCount = entity.getViewCount();
        this.content = entity.getContent();
    }
}
