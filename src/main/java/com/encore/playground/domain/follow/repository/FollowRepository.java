package com.encore.playground.domain.follow.repository;

import com.encore.playground.domain.follow.entity.Follow;
import com.encore.playground.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.Transient;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로잉 수 count하기
    Long countByFromMember(String fromMemberId);

    // 팔로워 수 count하기
    Long countByToMember(String toMemberId);

    // 언팔로우 했을 때, delete
    @Transactional
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);

}
