package com.encore.playground.domain.follow.service;


import com.encore.playground.domain.follow.dto.FollowDto;
import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.repository.FollowRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetIdDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
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
    public void follow(FollowGetIdDto followGetIdDto, MemberGetMemberIdDto memberIdDto) {

        MemberDto fromMember = memberService.getMemberByUserid(memberIdDto.getUserid());
        MemberDto toMember = memberService.getMember(MemberGetIdDto.builder().id(followGetIdDto.getToId()).build());
        followRepository.save(FollowDto.builder()
                .fromMember(fromMember.toEntity())
                .toMember(toMember.toEntity())
                .followDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    // 사용자가 다른 사용자를 unfollow
    public void unfollow(FollowGetIdDto followGetIdDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto fromMember = memberService.getMemberByUserid(memberIdDto.getUserid());
        MemberDto toMember = memberService.getMember(MemberGetIdDto.builder().id(followGetIdDto.getToId()).build());
        followRepository.deleteByFromMemberAndToMember(fromMember.toEntity(), toMember.toEntity());
    }

}
