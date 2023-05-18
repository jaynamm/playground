package com.encore.playground.domain.member.controller;

import com.encore.playground.domain.member.dto.MemberDTO;
import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import com.encore.playground.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberAPIController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberSecurityService memberSecurityService;
    private final TokenService tokenService;

    /**
     * POST - 로그인 사용자 정보 데이터 전달
     * @param loginMember
     * @return 사용자 토큰 생성 후 리액트로 전달
     */

    @PostMapping("/login")
    public ResponseEntity loginCheck(@RequestBody Map<String, String > loginMember) {

        System.out.println("[MemberAPIController:/api/member/login] ::: loginCheck()");

        // TODO : 유저 정보를 확인할 때 Security 를 사용할지 별도로 Service 에 추가해서 확인할지 생각

        // UserDetails member = memberSecurityService.loadUserByUsername(loginMember.get("userid"));
        Member member = memberService.getMemberByUserid(loginMember.get("userid"));
        String userid = member.getUserid();
        String nickname = member.getNickname();

        // 패스워드가 맞는지 확인한다.
        if(!passwordEncoder.matches(loginMember.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 로그인해서 아이디 확인하고 패스워드 맞는지 확인 후에 토큰 생성
        String token = tokenService.generateToken(userid);

        /*
            TODO : 로그인할 때 보내줘야할 데이터 - 아이디, 닉네임, 이메일, 토큰(헤더로 보내기 때문에 이후에 제외해도 될듯)
         */

        // 토큰을 담아보낼 HashMap 생성
        Map<String, String> loginRes = new HashMap<>();
        loginRes.put("userid", userid);
        loginRes.put("nickname", nickname);
        loginRes.put("token", token);

        // 헤더에 토큰을 저장한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        // TODO : statusCode 와 responseMessage 를 어떻게 구분해서 보낼 것인가

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.LOGIN_SUCCESS,
                        loginRes),
                headers,
                HttpStatus.OK
        );
    }

    @PostMapping("/login/test")
    public ResponseEntity login (@RequestBody Map<String, String > loginMember) {
        return new ResponseEntity<>(DefaultResponse.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginMember),
                                 HttpStatus.OK);
    }

    @PostMapping("/user/test")
    public Map userResponseTest(@RequestBody Map<String, String> getMember) {
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

    /**
     * POST - 회원가입 데이터 전달
     * @param registerMember
     * @return 회원가입 데이터를 가져와서 저장
     */

    @PostMapping("/signup")
    //public Map register(@RequestBody Map<String, String> registerMember) {
    public void register(@RequestBody MemberDTO registerMember) {
        System.out.println(registerMember);

        Boolean existed = memberService.existedUserCheck(registerMember.getUserid(), registerMember.getEmail());

        // 아이디 또는 이메일이 중복되지 않았다면
        if (!existed) {
            System.out.println("회원가입이 완료되었습니다.");
        }

        memberService.register(registerMember);
    }

    @PostMapping("/search/email")
    public ResponseEntity searchByEmail(@RequestBody Map<String, String> inputEmail) {

        System.out.println("[MemberAPIController] ::: searchByEmail() ");

        String email = inputEmail.get("email");

        String userid = memberService.searchByEmail(email);

        Map<String, String> getIdRes = new HashMap<>();
        getIdRes.put("userid", userid);

        return new ResponseEntity(getIdRes, HttpStatus.OK);
    }
}
