package com.encore.playground.domain.follow.dto;

import com.encore.playground.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowMyPageDto {
    // 사용자가 팔로우하는 사람의 수
    private Long followingCount;
    // 사용자를 팔로우하는 사람의 수
    private Long followerCount;
    // 사용자가 팔로우하는 리스트
    private List<MemberDto> followingList;
    // 사용자를 팔로우하는 리스트
    private List<MemberDto> followerList;
}
