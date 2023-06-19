package com.encore.playground.global.service;

import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.global.dto.RefreshTokenDto;
import com.encore.playground.global.dto.AccessTokenDto;
import com.encore.playground.global.jwt.JwtTokenProvider;
import com.encore.playground.global.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
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

//    public AccessTokenDto generateToken(String loginId) {
//        UserDetails member = memberSecurityService.loadUserByUsername(loginId);
//
//        // 토큰 생성할 때 유저 아이디와 권한을 넣어준다.
//        String userid = member.getUsername();
//        String roles = member.getAuthorities().stream().toList().get(0).toString();
//        AccessTokenDto accessTokenDto = JwtTokenProvider.generateToken(userid, roles);
//        // token에서 Refresh token의 정보를 refreshToken DTO로 만들어서 saveRefreshToken에 넣어준다.
//        RefreshTokenDto refreshTokenDto = RefreshTokenDto
//                .builder()
//                .refreshToken(accessTokenDto.getRefreshToken())
//                .memberId(accessTokenDto.getKey())
//                .build();
//        saveRefreshToken(refreshTokenDto);
//        return accessTokenDto;
//    }

    // refreshToken을 DB에 저장한다.
    public void saveRefreshToken(RefreshTokenDto refreshTokenDto) {
        refreshTokenRepository.save(refreshTokenDto.toEntity());

    }


    public boolean validateRefreshToken(RefreshTokenDto refreshTokenDto) {
        // refreshToken이 유효한 지 검증
        // refreshToken repository의 값을 불러와서 검증

        RefreshTokenDto storedRefreshTokenDto = new RefreshTokenDto(refreshTokenRepository.findById(refreshTokenDto.getId()).get());
        if (refreshTokenDto.getRefreshToken().equals(storedRefreshTokenDto.getRefreshToken())) {
//            JwtTokenProvider.regenerateAccessToken();
            return true;
        } else {
            return false;
        }
    }
}
