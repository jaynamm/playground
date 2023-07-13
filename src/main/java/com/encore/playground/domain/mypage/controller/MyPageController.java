package com.encore.playground.domain.mypage.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.mypage.dto.MyPageDto;
import com.encore.playground.domain.mypage.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public MyPageDto myPageMain(HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            return myPageService.myPage(memberIdDto);
        } else
            return null;
    }

    @GetMapping("/mypage/{userid}")
    public MyPageDto mypageOtherUserMain(@PathVariable String userid, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = MemberGetMemberIdDto.builder().userid(userid).build();
            return myPageService.myPage(memberIdDto);
        } else
            return null;
    }

}
