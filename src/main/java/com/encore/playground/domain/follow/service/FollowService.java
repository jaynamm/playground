package com.encore.playground.domain.follow.service;


import com.encore.playground.domain.follow.dto.FollowDto;
import com.encore.playground.domain.follow.dto.FollowGetMemberIdDto;
import com.encore.playground.domain.follow.repository.FollowRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberFollowDto;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    private final MemberService memberService;
    // 사용자가 다른 사용자를 follow
    public void follow(FollowGetMemberIdDto followGetMemberId) {
        MemberDto fromMember = memberService.getMember(MemberFollowDto.builder().memberId(followGetMemberId.getFromMemberId()).build());
        MemberDto toMember = memberService.getMember(MemberFollowDto.builder().memberId(followGetMemberId.getToMemberId()).build());
        followRepository.save(FollowDto.builder()
                .fromMember(fromMember.toMember())
                .toMember(toMember.toMember())
                .followDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    // 사용자가 다른 사용자를 unfollow
    public void unfollow(FollowGetMemberIdDto followGetMemberIdDto) {
        System.out.println(followGetMemberIdDto.getFromMemberId() + " " + followGetMemberIdDto.getToMemberId());
        followRepository.deleteByFromMemberIdAndToMemberId(followGetMemberIdDto.getFromMemberId(), followGetMemberIdDto.getToMemberId());
    }

}
