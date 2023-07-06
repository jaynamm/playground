package com.encore.playground.global.jwt;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.dto.MemberGetRoleDto;
import com.encore.playground.global.api.ResponseMessage;
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

        // refresh token이 있는지 확인하고 존재하면 검증한다.
        // 클라이언트에 unauthorized error code(401)을 보내서 request로 refresh token이 들어온다.
        if (httpRequest.getHeader("refresh-token") != null){
            System.out.println("[JwtAuthenticationFilter] ::: refreshToken이 존재합니다.");
            // refresh token을 받아와서 검증한다.
            System.out.println("[JwtAuthenticationFilter] ::: refreshToken을 검증합니다.");
            // refresh token이 유효한 경우,
            RefreshTokenValidateDto refreshTokenValidateDto = getRefreshToken(httpRequest);
            if (tokenService.validateRefreshToken(refreshTokenValidateDto)) {
                // 새로운 access token을 발급해서 보내준다.
                // 수정 (refreshtoken으로 refreshtoken table의 memberId 가져오기)
                String newAccessToken = tokenService.generateAccessToken(tokenService.getMemberIdFromRefreshToken(refreshTokenValidateDto)).getAccessToken();
                httpResponse.addHeader("Authorization", "Bearer " + newAccessToken);
                httpResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                // refresh token이 유효하지 않은 경우, 로그아웃 처리한다.
                System.out.println("refresh token이 유효하지 않습니다.");
                httpResponse.setHeader("responseMessage", ResponseMessage.LOG_OUT);
                httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }


        // Header 부분에서 JWT 정보를 가져온다.
        String token = jwtTokenProvider.resolveToken(httpRequest);

        System.out.println("[JwtAuthenticationFilter] ::: resolveToken() - token = " + token);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("================================");
            System.out.println("name : " + SecurityContextHolder.getContext().getAuthentication().getName());
            System.out.println("isAuthenticated : " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            System.out.println("authority : " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            System.out.println("principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            System.out.println("credential : " + SecurityContextHolder.getContext().getAuthentication().getCredentials());
            System.out.println("================================");

            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 유효합니다.");

            // access token의 userid를 추출하는 메소드
            // userid를 dto에 담는 로직
            MemberGetMemberIdDto memberGetMemberIdDto = MemberGetMemberIdDto.builder().userid(jwtTokenProvider.getUserPk(token)).build();
            System.out.println(memberGetMemberIdDto);
            MemberGetRoleDto memberGetRoleDto = MemberGetRoleDto.builder().role(jwtTokenProvider.getMemberRole(token)).build();
            System.out.println(memberGetRoleDto);// userid만 들어있는 dto를 request에 넣어주기
            httpRequest.setAttribute("memberIdDto", memberGetMemberIdDto);
            httpRequest.setAttribute("memberRoleDto", memberGetRoleDto);


        } else if (token == null) {
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 존재하지 않습니다.");
        } else if (!jwtTokenProvider.validateAccessToken(token)) {
            // access token이 유효하지 않음을 클라이언트에 전달한다.
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setHeader("responseMessage", ResponseMessage.ACCESS_TOKEN_EXPIRED);
            System.out.println("[JwtAuthenticationFilter] ::: 토큰이 만료됐습니다.");
        }

        chain.doFilter(request, response);
    }

    private RefreshTokenValidateDto getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-token");
//        String userid = request.getHeader("userid");
        return new RefreshTokenValidateDto(refreshToken);
    }


}
