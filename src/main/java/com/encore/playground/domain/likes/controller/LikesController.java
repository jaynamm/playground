package com.encore.playground.domain.likes.controller;

import com.encore.playground.domain.likes.dto.LikesGetIdDto;
import com.encore.playground.domain.likes.service.LikesService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/likes")
    public void likes(@RequestBody LikesGetIdDto likesGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            likesService.likes(likesGetIdDto, memberIdDto);
        }
    }

    @PostMapping("/likesCancel")
    public void likesCancel(@RequestBody LikesGetIdDto likesGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            likesService.likesCancel(likesGetIdDto, memberIdDto);
        }
    }



}
