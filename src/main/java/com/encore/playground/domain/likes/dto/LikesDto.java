package com.encore.playground.domain.likes.dto;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.likes.entity.Likes;
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
public class LikesDto {
    private Long id;
    private Feed feed;
    private Member member;
    private LocalDateTime createdDate;

    /**
     *  dto -> entity
     * @return like
     */
    public Likes toEntity() {
        return Likes.builder()
                .id(id)
                .feed(feed)
                .member(member)
                .createdDate(createdDate)
                .build();
    }

    /**
     * entity -> dto
     */
    public LikesDto(Likes likes) {
        this.id = likes.getId();
        this.feed = likes.getFeed();
        this.member = likes.getMember();
        this.createdDate = likes.getCreatedDate();
    }

}


