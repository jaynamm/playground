package com.encore.playground.domain.follow.dto;

import com.encore.playground.domain.follow.entity.Follow;
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
public class FollowDto {
    private Long id;
    private Member fromMember;
    private Member toMember;
    private LocalDateTime createdDate;

    /**
     * dto -> entity
     * @return follow
     */
    public Follow toEntity() {
        return Follow.builder()
              .id(id)
              .fromMember(fromMember)
              .toMember(toMember)
              .createdDate(createdDate)
              .build();
    }

    /**
     * entity -> dto
     * @param follow
     */
    public FollowDto(Follow follow) {
        this.id = follow.getId();
        this.fromMember = follow.getFromMember();
        this.toMember = follow.getToMember();
        this.createdDate = follow.getCreatedDate();
    }
}
