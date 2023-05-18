package com.encore.playground.global.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
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

        // Header 부분에서 JWT 정보를 가져온다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        System.out.println("[JwtAuthenticationFilter] ::: resolveToken() - token = " + token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 유효합니다.");
        }
        chain.doFilter(request, response);
    }
}
