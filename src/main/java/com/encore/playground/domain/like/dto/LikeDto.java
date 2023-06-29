package com.encore.playground.domain.like.dto;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.like.entity.Like;
import com.encore.playground.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private Long id;
    private Feed feed;
    private Member member;
    private LocalDateTime likeDate;

    /**
     *  dto -> entity
     * @return like
     */
    public Like toEntity() {
        return Like.builder()
                .id(id)
                .feed(feed)
                .member(member)
                .likeDate(likeDate)
                .build();
    }

    /**
     * entity -> dto
     */
    public LikeDto(Like like) {
        this.id = like.getId();
        this.feed = like.getFeed();
        this.member = like.getMember();
        this.likeDate = like.getLikeDate();
    }

}


