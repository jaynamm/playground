package com.encore.playground.domain.member.service;

import com.encore.playground.domain.member.dto.MemberDTO;
import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 시 파라미터를 전달받아 Entity 로 변환
     * Repository 를 통해 DB 에 저장
     * @param memberDTO
     */

    public void register(MemberDTO memberDTO) {
        Member member = new Member();

        member.setUserid(memberDTO.getUserid());
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setCurriculum(memberDTO.getCurriculum());
        member.setCreatedDate(LocalDateTime.now());
        member.setUpdatedDate(LocalDateTime.now());

        memberRepository.save(member);
    }

    /**
     * 아이디 찾기 시 파타미터를 전달받아 Userid 반환
     * @param memberDTO
     */

    public String searchIdForEmail(MemberDTO memberDTO) {
        Optional<Member> _member = this.memberRepository.findByEmail(memberDTO.getEmail());

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = _member.get();

        return member.getUserid();
    }
}
