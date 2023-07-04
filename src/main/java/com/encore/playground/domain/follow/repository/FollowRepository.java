package com.encore.playground.domain.follow.repository;

import com.encore.playground.domain.follow.entity.Follow;
import com.encore.playground.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로잉 수 count하기
    Long countByFromMember(Member fromMember);

    // 팔로워 수 count하기
    Long countByToMember(Member toMember);

    // 사용자가 팔로우하는 List
    List<Follow> findByFromMember(Member fromMember);

    // 사용자를 팔로우하는 List
    List<Follow> findByToMember(Member toMember);

    // 언팔로우 했을 때, delete
    @Transactional
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);

}
