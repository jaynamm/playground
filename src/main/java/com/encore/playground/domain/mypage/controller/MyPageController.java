package com.encore.playground.domain.mypage.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.mypage.dto.MyPageDto;
import com.encore.playground.domain.mypage.service.MyPageService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> myPageMain(HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.MYPAGE_USERPAGE_ACCESS,
                            myPageService.myPage(memberIdDto)
                    ),
                    HttpStatus.OK
            );
        } else
            return null;
    }

    @GetMapping("/mypage/{userid}")
    public ResponseEntity<?> mypageOtherUserMain(@PathVariable String userid, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            MemberGetMemberIdDto otherMemberIdDto = MemberGetMemberIdDto.builder().userid(userid).build();
            if (otherMemberIdDto.getUserid().equals(memberIdDto.getUserid())) {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.MYPAGE_USERPAGE_ACCESS,
                                myPageService.myPage(memberIdDto)
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.MYPAGE_OTHERPAGE_ACCESS,
                                myPageService.myPage(otherMemberIdDto)
                        ),
                        HttpStatus.OK
                );
            }
        } else
            return null;
    }

}
