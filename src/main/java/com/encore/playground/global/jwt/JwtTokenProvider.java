package com.encore.playground.global.jwt;

import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.global.dto.AccessTokenDto;
import com.encore.playground.global.dto.RefreshTokenDto;
import com.encore.playground.global.security.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component // 개발자가 직접 작성한 Class 를 Bean 으로 등록하기 위한 어노테이션이다.
public class JwtTokenProvider {

    private final MemberSecurityService memberSecurityService;

    // JWT 에서 사용할 시크릿 키를 만들어준다.
    private static final String JWT_ACCESS_TOKEN_SECRET = "secretKey1";
    private static final String JWT_REFRESH_TOKEN_SECRET = "secretKey2";
    // 토큰 유효 시간을 설정해준다.
    // Access Token의 유효 시간 -> 30분으로 설정한다.
    private static final long JWT_ACCESS_EXPIRATION_MS = 30 * 60 * 1000L;
    // Refresh Token의 유효 시간 -> 7일로 설정한다.
    private static final long JWT_REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000L;
    private static final String TOKEN_HEADER_NAME = "Authorization";

    /**
     * Access Token 생성 메서드
     * @param userid
     * @param roles
     * @return AccessTokenDto
     */

    public static AccessTokenDto generateAccessToken(String userid, String roles) {
        Date now = new Date();
        Date accessTokenExpiredDate = new Date(now.getTime() + JWT_ACCESS_EXPIRATION_MS);
        // 사용자 정보 설정 (JWT payload)
        Claims claims = Jwts.claims().setSubject(userid);
        claims.put("roles", roles);  // 정보는 key:value 형태로 저장된다.
        String accessToken = Jwts.builder()
                // .setSubject(userid) // 토큰 제목 -> Claim 으로 대체
                .setClaims(claims) // 사용자 정보 저장
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(accessTokenExpiredDate) // 토큰 만료시간 - 생성으로부터 30분
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_TOKEN_SECRET) // 암호화 알고리즘 설정
                .compact(); // 생성 완료

        return AccessTokenDto.builder().accessToken(accessToken).key(userid).build();
    }

    /**
     * Refresh Token 생성 메서드
     * @param userid
     * @param roles
     * @return RefreshTokenDto
     */


    public static RefreshTokenDto generateRefreshToken(String userid, String roles) {
        Date now = new Date();
        Date refreshTokenExpiredDate = new Date(now.getTime() + JWT_REFRESH_EXPIRATION_MS);
        Claims claims = Jwts.claims().setSubject(userid);
        claims.put("roles", roles);

        String refreshToken = Jwts.builder()
                // .setSubject(userid) // 토큰 제목 -> Claim 으로 대체
                .setClaims(claims) // 사용자 정보 저장
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(refreshTokenExpiredDate) // 토큰 만료시간 - 생성으로부터 14일
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_TOKEN_SECRET) // 암호화 알고리즘 생성
                .compact(); // 생성 완료

        return RefreshTokenDto.builder().refreshToken(refreshToken).memberId(userid).build();

    }



    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberSecurityService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserIdByRefreshToken(String refreshToken) {
        return Jwts.parser().setSigningKey(JWT_REFRESH_TOKEN_SECRET).parseClaimsJws(refreshToken).getBody().getSubject();
    }

    public String getMemberRole(String token) {
        return (String) Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJws(token).getBody().get("roles");
    }

    public String getMemberRoleByRefreshToken(String refreshToken) {
        return (String) Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJws(refreshToken).getBody().get("roles");
    }



    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "Bearer {TOKEN}'
    public String resolveToken(HttpServletRequest request) {
        // Request Header 에서 토큰을 가져완다.
        String bearerToken = request.getHeader(TOKEN_HEADER_NAME);

        // 토큰을 가지고 있고 토큰이 Bearer 로 시직한다면 "Bearer " 이후의 값을 가져온다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 가져온 토큰이 없으면 null
        return null;
    }

     //토큰의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 로그인이 풀리도록 처리 - 클라이언트에 토큰 삭제 요청
            return false;
        }
    }

    public static boolean validateRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_REFRESH_TOKEN_SECRET).parseClaimsJws(refreshToken);
            return!claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
        // refresh token 검증 // repository를 거쳐야 하기 때문에 TokenService에 작성
    }

}
