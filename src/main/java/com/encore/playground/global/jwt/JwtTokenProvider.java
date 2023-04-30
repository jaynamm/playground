package com.encore.playground.global.jwt;

import com.encore.playground.domain.member.service.MemberSecurityService;
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
    private static final String JWT_SECRET = "secretKey";
    // 토큰 유효 시간을 설정해준다. -> 30분으로 설정해주었다.
    private static final long JWT_EXPIRATION_MS = 30 * 60 * 1000L;

    private static final String TOKEN_HEADER_NAME = "Authorization";

    /**
     * JWT 토큰 생성 메서드
     * @param userid
     * @param roles
     * @return JWT 토큰을 생성해준다.
     */
    public static String generateToken(String userid, String roles) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        // 사용자 정보 설정
        Claims claims = Jwts.claims().setSubject(userid);
        claims.put("roles", roles); // 정보는 key:value 형태로 저장된다.

        return Jwts.builder()
                // .setSubject(userid) // 토큰 제목 -> Claim 으로 대체
                .setClaims(claims) // 사용자 정보 저장
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료시간 - 생성 시간으으로부터 30분으로 설정
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET) // 암호화 알고리즘 설정
                .compact(); // 생성 완료
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberSecurityService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
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

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
