package com.encore.playground.domain.likes.controller;

import com.encore.playground.domain.likes.dto.LikesGetIdDto;
import com.encore.playground.domain.likes.service.LikesService;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> likes(@RequestBody LikesGetIdDto likesGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            try {
                likesService.likes(likesGetIdDto, memberIdDto);
            } catch (Exception e) {
                return new ResponseEntity<>(DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.LIKES_ALREADY_EXIST), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return null;
    }

    @PostMapping("/likesCancel")
    public ResponseEntity<?> likesCancel(@RequestBody LikesGetIdDto likesGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            try {
                likesService.likesCancel(likesGetIdDto, memberIdDto);
            } catch (Exception e) {
                return new ResponseEntity<>(DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.LIKES_NOT_EXIST), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return null;
    }
}
