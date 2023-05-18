package com.encore.playground.domain.member.service;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 로그인시 userid 를 통해 멤버 확인 후 가져옴
     * @param userid
     * @return MemberDto
     */
    public MemberDto getMemberByUserid(String userid) {
        Optional<Member> _member = memberRepository.findByUserid(userid);

        // 멤버 유무 확인
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
        // 멤버가 있으면 멤버를 가져온다.
        MemberDto memberDto = new MemberDto(_member.get());

        return memberDto;
    }

    public Boolean existedUserCheck(String userid, String email) {
        Optional<Member> memberForUserid = this.memberRepository.findByUserid(userid);
        Optional<Member> memberForEmail = this.memberRepository.findByEmail(email);

        // TODO : 아이디와 이메일의 중복 체크를 어떻게 해줘야할지 생각해보기

        // 아이디와 이메일이 둘 중 하나라도 중복이 되었다면 중복된 것으로 본다.
        if (memberForUserid.isPresent() || memberForEmail.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 회원 가입 시 파라미터를 전달받아 Entity 로 변환
     * Repository 를 통해 DB 에 저장
     * @param memberDTO
     */

    public void register(MemberDto memberDTO) {
        Member member = new Member();
        member.setUserid(memberDTO.getUserid());
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setCurriculum(memberDTO.getCurriculum());

        memberRepository.save(member);
    }


    /**
     * 아이디 찾기 시 파타미터를 전달받아 Userid 반환
     * @param email
     * @return member.getUserid();
     */

    public String searchIdByEmail(String email) {
        Optional<Member> _member = this.memberRepository.findByEmail(email);

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = _member.get();

        return member.getUserid();
    }


    /**
     * 비밀번호 찾기 시 userid 와 email 을 가져와 멤버 확인 후 임시 비밀번호 발급
     * @param userid
     * @param email
     * @return randomPassword
     */

    public String searchPasswordByUseridAndEmail(String userid, String email) {
        Optional<Member> _member = this.memberRepository.findByUseridAndEmail(userid, email);

        // 랜덤 패스워드 초기화
        String randomPassword = "";

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        } else {
            // 임시 패스워드로 변경을 위해서 사용자 정보를 가자온다.
            MemberDto memberDto = new MemberDto(_member.get());

            char[] charSet = new char[] {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    '!', '@', '#', '$', '%', '^', '&' };
            StringBuffer sb = new StringBuffer();
            SecureRandom sr = new SecureRandom();

            int idx = 0;
            int len = charSet.length;
            for (int i=0; i<10; i++) {
                // 강력한 난수를 발생시키기 위해 SecureRandom 을 사용한다.
                idx = sr.nextInt(len);
                sb.append(charSet[idx]);
            }

            randomPassword = sb.toString();

            // 암호화 시킨 패스워드를 저장한다.
            memberDto.setPassword(passwordEncoder.encode(randomPassword));
            // @LastModifiedDate 로 자동으로 수정시 시간이 변경됨
            // memberDto.setModifiedDate(LocalDateTime.now());
            memberRepository.save(memberDto.toMember());
        }

        return randomPassword;
    }
}
