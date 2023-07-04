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
import java.util.List;

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

    // mypage에서 조회할 follow 정보

    // 사용자가 follow하는 다른 사용자 count
    public Long getFollowingCount(MemberDto memberDto) {
        return followRepository.countByFromMember(memberDto.toEntity());
    }

    // 사용자를 follow하는 다른 사용자 count
    public Long getFollowerCount(MemberDto memberDto) {
        return followRepository.countByToMember(memberDto.toEntity());
    }

    // 사용자가 follow하는 사용자 List
//    public List<MemberDto> getFollowingList(MemberDto memberDto) {
//        List<FollowDto> followList = followRepository.findByFromMember(memberDto.toEntity()).stream().map(FollowDto::new).toList();
//        List<MemberDto> followingMemberList =
//    }




}
