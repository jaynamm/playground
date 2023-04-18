package com.encore.playground.domain.member.controller;

import com.encore.playground.domain.member.dto.MemberDTO;
import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.repository.MemberRepository;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test/member")
public class MemberTestController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public ResponseEntity<Member> memberList() {
        Optional<Member> member = memberRepository.findByEmail("aa@aa.aa");

        return new ResponseEntity(member, HttpStatus.OK);
    }

}
