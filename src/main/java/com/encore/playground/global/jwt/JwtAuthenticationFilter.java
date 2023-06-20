package com.encore.playground.global.jwt;

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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("[JwtAuthenticationFilter] ::: doFilter() ");

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Header 부분에서 JWT 정보를 가져온다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        System.out.println("[JwtAuthenticationFilter] ::: resolveToken() - token = " + token);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 유효합니다.");
        } else if (token == null) {
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 존재하지 않습니다.");
        } else if (!jwtTokenProvider.validateAccessToken(token)) {
            // access token이 유효하지 않음을 front에 전달한다.
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 만료됐습니다.");
        }

        chain.doFilter(request, response);
    }
}
