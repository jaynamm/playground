package com.encore.playground.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowGetMemberIdDto {
    private String fromMemberId;
    private String toMemberId;
}
