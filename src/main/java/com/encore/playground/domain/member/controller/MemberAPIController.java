package com.encore.playground.domain.member.controller;

import com.encore.playground.domain.member.repository.MemberRepository;
import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberAPIController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final MemberSecurityService memberSecurityService;

    /**
     * POST - 로그인 사용자 정보 데이터 전달
     * @param loginMember
     * @return 사용자 토큰 생성
     */

    @PostMapping("login")
    public String loginCheck(@RequestBody Map<String, String > loginMember) {
        // 로그인한 사용자의 userid 를 가져와서 사용자 정보를 가져옴
        UserDetails member = memberSecurityService.loadUserByUsername(loginMember.get("userid"));

        if(!passwordEncoder.matches(loginMember.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String userid = member.getUsername();
        String roles = member.getAuthorities().stream().toList().get(0).toString();

        return jwtTokenProvider.generateToken(userid, roles);
    }

    /**
     * POST - Header 에 JWT 데이터 전달
     * @return 토큰 인증이 정상적으로 완료되면 "user ok" 반환
     */

    @PostMapping("/user/test")
    public Map userResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","user ok");
        return result;
    }

    /**
     * POST - Header 에 JWT 데이터 전달
     * @return 토큰 인증이 정상적으로 완료되면 "admin ok" 반환
     */

    @PostMapping("/admin/test")
    public Map adminResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","admin ok");
        return result;
    }
}
