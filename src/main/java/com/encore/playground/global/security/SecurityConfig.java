package com.encore.playground.global.security;

import com.encore.playground.global.jwt.JwtAuthenticationFilter;
import com.encore.playground.global.jwt.JwtTokenProvider;
import com.encore.playground.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //기본적인 보안을 활성화
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable() // REST API 를 사용하기 위해 http 기본 설정 해제
                .csrf().disable() // REST API 를 사용하기 위해 csrf 보안 비활성화
                .headers()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 토큰 기반 인증이므로 세션을 사용하지 않는다.
                .and()
                .authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, tokenService),
                        UsernamePasswordAuthenticationFilter.class)

                // 로그인 구현
                .formLogin()
                .loginPage("/member/login") // 로그인 페이지
                .defaultSuccessUrl("/home") // 로그인 성공
                .usernameParameter("userid") // loadUserByUsername 의 username 파라미터값 변경

                // 로그아웃 구현
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        ;

        return http.build();
    }

    //암호화에 필요한 PasswordEncoder Bean 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager Bean 등록
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
