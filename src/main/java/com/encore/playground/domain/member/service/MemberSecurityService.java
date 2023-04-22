package com.encore.playground.domain.member.service;

import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.repository.MemberRepository;
import com.encore.playground.global.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;


    /*

    https://cupdisin.tistory.com/91 참고
     */
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Optional<Member> _member = this.memberRepository.findByUserid(userid);

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("가입되지 않은 사용자입니다.");
        }

        Member member = _member.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        if ("admin".equals(userid)) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(member.getUserid(), member.getPassword(), authorityList);
    }
}
