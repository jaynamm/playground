package com.encore.playground.domain.mypage.service;

import com.encore.playground.domain.follow.dto.FollowMyPageDto;
import com.encore.playground.domain.follow.service.FollowService;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.mypage.dto.MyPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final FollowService followService;

//    public MyPageDto myPage(MemberGetMemberIdDto memberIdDto) {
//        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
//        FollowMyPageDto followDto = followService.getFollowMyPageDto();
//    }
}
