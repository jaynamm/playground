package com.encore.playground.domain.member.controller;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.form.MemberRegisterForm;
import com.encore.playground.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    /**
     * GET - 로그인 화면 페이지 이동
     * @return 로그인 페이지 이동 (인덱스 -> 로그인 화면)
     */

    @GetMapping("/login")
    public String loginForm() {
        return "member/login_form";
    }

    /**
     * GET - 회원가입 화면 페이지 이동
     * @param memberRegisterForm
     * @return 회원가입 화면으로 이동
     */

    @GetMapping("/signup")
    public String registerForm(MemberRegisterForm memberRegisterForm) {
        return "member/register_form";
    }

    /**
     * POST - 회원가입 데이터 전달
     * @param memberDTO
     * @param memberRegisterForm
     * @param bindingResult
     * @return Member 생성 후 로그인 페이지로 이동
     */

    @PostMapping("/signup")
    public String register(@ModelAttribute MemberDto memberDTO,
                           @Valid MemberRegisterForm memberRegisterForm, BindingResult bindingResult) {
        // 파라미터 바인딩 확인
        if (bindingResult.hasErrors()) {
            return "member/register_form";
        }

        // 파라미터 validation 확인
        if (!memberRegisterForm.getPassword().equals(memberRegisterForm.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
            return "member/register_form";
        }

        try {
            // 파라미터를 받아서 Service 로 전달
            memberService.register(memberDTO);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "member/register_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/register_form";
        }

        return "redirect:/member/login";
    }

    /**
     * GET - 아이디 찾기 페이지 이동
     * 아래의 2 가지 방법으로 아이디 찾기 진행
     * 1. 이메일 입력 후 가입한 이메일로 아이디 찾기
     * 2. 아이디 입력 후 이메일 인증 완료 후 비밀번호 재설정 화면
     * @return 아이디 찾기 페이지 이동
     */

    @GetMapping("/search/id")
    public String searchIdForm() {
        return "member/search_id_form";
    }

    /**
     * POST - 아이디 찾기를 위한 이메일 데이터 전달
     * @param memberDTO
     * @param model
     * @return 입력된 이메일에 해당 하는 유저 아이디 반환
     */

    @PostMapping("search/id")
    public String searchIdForEmail(@ModelAttribute MemberDto memberDTO, Model model) {
        // 파라미터를 받아서 Service 로 전달
        String userid = memberService.searchIdByEmail(memberDTO.getEmail());
        model.addAttribute("userid", userid);

        return "member/search_id_check";
    }
}
