package com.encore.playground.global.service;

import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.global.dto.RefreshTokenDto;
import com.encore.playground.global.dto.AccessTokenDto;
import com.encore.playground.global.dto.RefreshTokenValidateDto;
import com.encore.playground.global.jwt.JwtTokenProvider;
import com.encore.playground.global.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TokenService {
    private final MemberSecurityService memberSecurityService;
    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * 유저 아이디를 통해 아이디와 유저 권한 값을 넣어 토큰을 생성해준다.
     * access Token
     * @param loginId
     * @return AccessTokenDto
     */

    public AccessTokenDto generateAccessToken(String loginId) {
        UserDetails member = memberSecurityService.loadUserByUsername(loginId);

        // 토큰 생성할 때 유저 아이디와 권한을 넣어준다.
        String userid = member.getUsername();
        String roles = member.getAuthorities().stream().toList().get(0).toString();
        AccessTokenDto accessTokenDto = JwtTokenProvider.generateAccessToken(userid, roles);
        return accessTokenDto;
    }

    /**
     * 유저 아이디를 통해 아이디와 유저 권한 값을 넣어 토큰을 생성해준다.
     * refresh token
     *
     * @param loginId
     * @return RefreshTokenDto
     */

    public RefreshTokenDto generateRefreshToken(String loginId) {
        UserDetails member = memberSecurityService.loadUserByUsername(loginId);

        // 토큰 생성할 때 유저 아이디와 권한을 넣어준다.
        String userid = member.getUsername();
        String roles = member.getAuthorities().stream().toList().get(0).toString();
        RefreshTokenDto refreshTokenDto = JwtTokenProvider.generateRefreshToken(userid, roles);
        saveRefreshToken(refreshTokenDto);
        return refreshTokenDto;
    }


    // refreshToken을 DB에 저장한다.
    public void saveRefreshToken(RefreshTokenDto refreshTokenDto) {
        refreshTokenRepository.save(refreshTokenDto.toEntity());
    }

    // refreshToken이 유효한 지 검증
    // refreshToken repository의 값을 불러와서 검증해야 함

    public boolean validateRefreshToken(RefreshTokenValidateDto refreshTokenDto) {
        if (JwtTokenProvider.validateRefreshToken(refreshTokenDto.getRefreshToken())) {
            System.out.println(refreshTokenDto.getRefreshToken());
            System.out.println(refreshTokenRepository.findByRefreshToken(refreshTokenDto.getRefreshToken()).get().getRefreshToken());
            if (refreshTokenDto.getRefreshToken().equals(refreshTokenRepository.findByRefreshToken(refreshTokenDto.getRefreshToken()).get().getRefreshToken())) {
                System.out.println("동일한 refresh token이 존재합니다.");
                System.out.println(refreshTokenDto);
//                generateAccessToken(refreshTokenDto.getMemberId());
                return true;
            } else {
                System.out.println("동일한 refresh token이 존재하지 않습니다.");
                // 로그인이 풀리도록 처리 - 클라이언트에 토큰 삭제 요청
                return false;
            }
        } else {
            System.out.println("refresh token이 만료됐습니다.");
            // 로그인이 풀리도록 처리 - 클라이언트에 토큰 삭제 요청
            return false;
        }
    }

    public String getMemberIdFromRefreshToken(RefreshTokenValidateDto refreshTokenValidateDto) {
        return refreshTokenRepository.findByRefreshToken(refreshTokenValidateDto.getRefreshToken()).get().getMemberId();
    }
}
