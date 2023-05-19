package com.encore.playground.domain.member.repository;

import com.encore.playground.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserid(String userid);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUseridAndEmail(String userid, String email);
}
