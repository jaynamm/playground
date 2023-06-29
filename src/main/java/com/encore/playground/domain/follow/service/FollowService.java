package com.encore.playground.domain.follow.service;


import com.encore.playground.domain.follow.dto.FollowDto;
import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.repository.FollowRepository;
import com.encore.playground.domain.member.dto.MemberDto;
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
    public void follow(FollowGetIdDto followGetIdDto) {
        MemberDto fromMember = memberService.getMember(followGetIdDto.getFromId());
        MemberDto toMember = memberService.getMember(followGetIdDto.getToId());
        followRepository.save(FollowDto.builder()
                .fromMember(fromMember.toMember())
                .toMember(toMember.toMember())
                .followDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    // 사용자가 다른 사용자를 unfollow
    public void unfollow(FollowGetIdDto followGetIdDto) {
        MemberDto fromMember = memberService.getMember(followGetIdDto.getFromId());
        MemberDto toMember = memberService.getMember(followGetIdDto.getToId());
        followRepository.deleteByFromMemberAndToMember(fromMember.toMember(), toMember.toMember());
    }

}
