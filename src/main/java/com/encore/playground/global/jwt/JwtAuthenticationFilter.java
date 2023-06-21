package com.encore.playground.global.jwt;

import com.encore.playground.global.dto.RefreshTokenValidateDto;
import com.encore.playground.global.service.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("[JwtAuthenticationFilter] ::: doFilter() ");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // refresh token이 있는지를 검증한다.
        if (httpRequest.getHeader("refreshToken") != null){
            System.out.println("[JwtAuthenticationFilter] ::: refreshToken이 존재합니다.");
            // refresh token을 받아와서 검증한다.
            System.out.println("[JwtAuthenticationFilter] ::: refreshToken을 검증합니다.");
            tokenService.validateRefreshToken(getRefreshToken(httpRequest));
            // refresh token이 유효한 경우, 새로운 access token을 발급해서 보내준다.
            // refresh token이 유효하지 않은 경우, 로그아웃 처리한다.

        }

        // Header 부분에서 JWT 정보를 가져온다.
        String token = jwtTokenProvider.resolveToken(httpRequest);

        System.out.println("[JwtAuthenticationFilter] ::: resolveToken() - token = " + token);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 유효합니다.");
        } else if (token == null) {
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 존재하지 않습니다.");
        } else if (!jwtTokenProvider.validateAccessToken(token)) {
            // access token이 유효하지 않음을 클라이언트에 전달한다.
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 만료됐습니다.");
        }

        chain.doFilter(request, response);
    }

    private RefreshTokenValidateDto getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("RefreshToken");
        String userid = request.getHeader("Userid");
        System.out.println(refreshToken + userid);
        return new RefreshTokenValidateDto(refreshToken, userid);
    }


}
