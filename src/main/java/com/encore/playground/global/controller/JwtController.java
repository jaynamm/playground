package com.encore.playground.global.controller;

import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import com.encore.playground.global.dto.RefreshTokenValidateDto;
import com.encore.playground.global.jwt.JwtTokenProvider;
import com.encore.playground.global.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class JwtController {

    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request) {
        System.out.println("request가 들어왔습니다. " + request);
        System.out.println("request header의 값은 " + request.getHeader("refresh-token"));
        RefreshTokenValidateDto refreshTokenDto = new RefreshTokenValidateDto(request.getHeader("refresh-token"));
        System.out.println(refreshTokenDto.getRefreshToken());
        if (tokenService.validateRefreshToken(refreshTokenDto)) {
            String userid = jwtTokenProvider.getUserIdByRefreshToken(refreshTokenDto.getRefreshToken());
            String roles = jwtTokenProvider.getMemberRoleByRefreshToken(refreshTokenDto.getRefreshToken());
            System.out.println(userid);
            System.out.println(roles);
            String newAccessToken = "Bearer " + JwtTokenProvider.generateAccessToken(userid, roles).getAccessToken();
            System.out.println("새롭게 발급된 access token 입니다. " + newAccessToken);
            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseMessage.LOG_OUT, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
