package com.encore.playground.domain.member.controller;

import com.encore.playground.domain.member.dto.*;
import com.encore.playground.domain.member.service.MemberSecurityService;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import com.encore.playground.global.dto.AccessTokenDto;
import com.encore.playground.global.dto.RefreshTokenDto;
import com.encore.playground.global.service.TokenService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
@Tag(name = "Member", description = "멤버 테이블 관련 API")
public class MemberAPIController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberSecurityService memberSecurityService;
    private final TokenService tokenService;
    private final Gson gson = new Gson();

    /**
     * POST - 로그인 사용자 정보 데이터 전달
     * @param loginData
     * @return ResponseEntity(loginRes, headers, HttpStatus.OK)
     */
    @Operation(hidden = true)
    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody Map<String, String> loginData) {

        System.out.println("[MemberAPIController:/api/member/login] ::: loginCheck()");

        // TODO : 유저 정보를 확인할 때 Security 를 사용할지 별도로 Service 에 추가해서 확인할지 생각 필요
        // UserDetails member = memberSecurityService.loadUserByUsername(loginMember.get("userid"));

        // API 로 가져온 userid 로 멤버가 있는지 확인
        MemberDto member = memberService.getMemberByUserid(loginData.get("userid"));

        // 토큰에 넣어주기 위해서 userid, nickname 을 가져온다.
        String userid = member.getUserid();
        String nickname = member.getNickname();

        // 로그인시 입력한 패스워드가 맞는지 확인한다.
        if(!passwordEncoder.matches(loginData.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 로그인해서 아이디 확인하고 패스워드 맞는지 확인 후에 토큰 생성
        AccessTokenDto accessTokenDto = tokenService.generateAccessToken(userid);
        RefreshTokenDto refreshTokenDto = tokenService.generateRefreshToken(userid);


        String accessToken = accessTokenDto.getAccessToken();
        String refreshToken = refreshTokenDto.getRefreshToken();


        // TODO : 로그인할 때 보내줘야할 데이터 - 아이디, 닉네임, 이메일, 토큰(헤더로 보내기 때문에 이후에 제외해도 될듯)
        // header에 access token을 넣어서 보내고 body에 refresh token을 넣어서 보낸다.

        // API 로 보낼 데이터를 HashMap 에 담아서 보낸다.
        // body에 refresh token을 넣어서 보낸다.
        Map<String, String> loginRes = new HashMap<>();
//        loginRes.put("userid", userid);
//        loginRes.put("nickname", nickname);
        loginRes.put("refresh-token", refreshToken);

        // 헤더에 access 토큰을 저장한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // TODO : statusCode 와 responseMessage 를 어떻게 구분해서 보낼 것인가

        return new ResponseEntity<>(loginRes, headers, HttpStatus.OK);
    }

    /**
     * 회원가입 화면에서 아이디 중복 체크
     * @param memberIdDto 사용자가 입력한 userId가 들어있는 Dto
     * @return ResponseEntity
     */
    @Operation(summary = "회원가입 화면에서 아이디 중복 체크", description = "해당 String이 이미 있는 ID와 중복되는지 체크한다.")
    @PostMapping("/checkid")
    public Map<String, String> checkUserid(@RequestBody MemberGetMemberIdDto memberIdDto) {
        Boolean existed = memberService.isExistUserid(memberIdDto);
        Map<String, String> result = new HashMap<>();
        // 중복되지 않았을 경우
        if (!existed) {
            result.put("responseMessage", ResponseMessage.USERID_USEABLE);
        } else { // 중복되었을 경우
            result.put("responseMessage", ResponseMessage.USERID_EXIST);
        }
        return result;
    }

    /**
     * POST - 회원가입 데이터 전달
     * @param registerMember
     * @return 회원가입 데이터를 가져와서 저장
     */
    @Operation(hidden = true)
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody MemberDto registerMember) {
        System.out.println(registerMember);

        // TODO: 회원가입 시 사용자 아이디와 이메일을 중복체크하는데 어떤 방식으로 확인을 해야할지 생각해봐야 한다.

        // 현재로써는 Boolean 타입으로 각 여부를 체크해서 값을 가져온다.
        Boolean existed = memberService.existedUserCheck(registerMember.getUserid(), registerMember.getEmail());

        if (!existed) { // 아이디와 이메일이 중복되지 않았을 경우
            memberService.register(registerMember);
            System.out.println("회원가입이 완료되었습니다.");
        } else { // 아이디 또는 이메일이 중복되었을 경우
            System.out.println("회원가입에 실패하였습니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 이메일로 아이디 찾기
     * @param inputEmail
     * @return getIdRes
     */
    @Operation(hidden = true)
    @PostMapping("/search/id")
    public ResponseEntity<?> searchIdByEmail(@RequestBody Map<String, String> inputEmail) {

        System.out.println("[MemberAPIController] ::: searchByEmail() ");

        // Map 에서 email 이라는 키 값으로 이메일 정보를 가져온다.
        String email = inputEmail.get("email");

        // 이메일을 통해 멤버 데이터를 가져온다.
        String userid = memberService.searchIdByEmail(email);

        Map<String, String> getFoundId = new HashMap<>();
        getFoundId.put("userid", userid);

        return new ResponseEntity<>(getFoundId, HttpStatus.OK);
    }

    /**
     * 유저 아이디와 이메일로 패스워드 찾기
     * @param memberSearchDto
     * @return
     */
    @Operation(hidden = true)
    @PostMapping("/search/password")
    public ResponseEntity<?> searchPasswordByUseridAndEmail(@RequestBody MemberSearchDto memberSearchDto) {
        String userid = memberSearchDto.getUserid();
        String email = memberSearchDto.getEmail();

        String getRandomPassword = memberService.searchPasswordByUseridAndEmail(userid, email);

        Map<String, String> randomPassword = new HashMap<>();
        randomPassword.put("randomPassword", getRandomPassword);

        return new ResponseEntity<>(randomPassword, HttpStatus.OK);
    }

    /**
     * 비밀번호 변경 기능
     * @param memberPasswordDto - password
     * @param request
     * @return ? - 비밀번호 변경 성공 여부
     */
    @Operation(summary = "비밀번호 변경 기능", description = "이미 로그인한 사용자의 비밀번호를 변경한다.")
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody MemberPasswordDto memberPasswordDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            String userid = memberIdDto.getUserid();
            String password = memberPasswordDto.getPassword();
            memberService.changePassword(userid, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return null;
    }

    /**
     * 스킬셋 저장 페이지에서 사용자가 지정한 스킬셋을 멤버 테이블에 저장
     * @param skill 스킬셋 JSON
     * @param request
     */
    @Operation(summary = "스킬셋 저장", description = "스킬셋 저장 페이지에서 사용자가 지정한 스킬셋을 멤버 테이블에 저장한다.")
    @PostMapping("/skills")
    public ResponseEntity<?> setSkills(@RequestBody MemberSetSkillDto skill, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            String skills = gson.toJson(skill);
            memberService.setMemberSkills(skills, memberIdDto);
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.SETSKILL_SUCCESS
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.NOT_FOUND_USER
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * 채용 추천 시스템에서 해당 멤버가 갖고 있는 스킬셋을 불러오기
     * @param request
     * @return 스킬셋 JSON
     */

    @Operation(summary = "스킬셋 불러오기", description = "채용 추천 시스템에서 해당 멤버가 갖고 있는 스킬셋을 불러온다.")
    @GetMapping("/skills")
    public ResponseEntity<?> getSkills(HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            String skills = memberService.getMemberSkills(memberIdDto);
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.GETSKILL_SUCCESS,
                            gson.fromJson(skills, Map.class)
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.NOT_FOUND_USER
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
