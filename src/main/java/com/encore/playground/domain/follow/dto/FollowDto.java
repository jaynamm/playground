package com.encore.playground.domain.follow.dto;

import com.encore.playground.domain.follow.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowDto {
    private Long id;
    private String fromMemberId;
    private String toMemberId;
    private LocalDateTime followDate;

    /**
     * dto -> entity
     * @return follow
     */
    public Follow toEntity() {
        return Follow.builder()
              .id(id)
              .fromMemberId(fromMemberId)
              .toMemberId(toMemberId)
              .followDate(followDate)
              .build();
    }

    /**
     * entity -> dto
     * @param follow
     */
    public FollowDto(Follow follow) {
        this.id = follow.getId();
        this.fromMemberId = follow.getFromMemberId();
        this.toMemberId = follow.getToMemberId();
        this.followDate = follow.getFollowDate();
    }



}
