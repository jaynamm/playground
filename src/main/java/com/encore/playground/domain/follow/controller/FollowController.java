package com.encore.playground.domain.follow.controller;

import com.encore.playground.domain.follow.dto.FollowGetIdDto;
import com.encore.playground.domain.follow.service.FollowService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestBody FollowGetIdDto followGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            try {
                followService.follow(followGetIdDto, memberIdDto);
            } catch (Exception e) {
                return new ResponseEntity<>(DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.FOLLOW_ALREADY_EXIST), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return null;
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestBody FollowGetIdDto followGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            try {
                followService.unfollow(followGetIdDto, memberIdDto);
            } catch (Exception e) {
                return new ResponseEntity<>(DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.FOLLOW_NOT_EXIST), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return null;
    }
}
