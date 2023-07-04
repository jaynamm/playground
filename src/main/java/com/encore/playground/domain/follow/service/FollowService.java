package com.encore.playground.domain.follow.service;


import com.encore.playground.domain.follow.dto.FollowDto;
import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.dto.FollowMyPageDto;
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
                .createdDate(LocalDateTime.now())
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
    public List<MemberDto> getFollowingList(MemberDto memberDto) {
        List<FollowDto> followDtoList = followRepository.findByFromMember(memberDto.toEntity()).stream().map(FollowDto::new).toList();
        return followDtoList.stream().map(FollowDto::getToMember).map(MemberDto::new).toList();
    }

    // 사용자를 follow하는 사용자 List
    public List<MemberDto> getFollowerList(MemberDto memberDto) {
        List<FollowDto> followDtoList = followRepository.findByToMember(memberDto.toEntity()).stream().map(FollowDto::new).toList();
        return followDtoList.stream().map(FollowDto::getFromMember).map(MemberDto::new).toList();
    }

    // FollowMyPageDto build
    public FollowMyPageDto getFollowMyPageDto(MemberDto memberDto) {
        return FollowMyPageDto.builder()
                .followingCount(getFollowingCount(memberDto))
                .followerCount(getFollowerCount(memberDto))
                .followingList(getFollowingList(memberDto))
                .followerList(getFollowerList(memberDto))
                .build();
    }


}
