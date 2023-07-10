package com.encore.playground.domain.follow.controller;

import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.service.FollowService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public void follow(@RequestBody FollowGetIdDto followGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            followService.follow(followGetIdDto, memberIdDto);
        }
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestBody FollowGetIdDto followGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            followService.unfollow(followGetIdDto, memberIdDto);
        }
    }
}
