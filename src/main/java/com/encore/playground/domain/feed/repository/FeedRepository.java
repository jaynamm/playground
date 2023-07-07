package com.encore.playground.domain.feed.repository;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 피드 글을 저장하는 리포지토리
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<List<Feed>> findByMemberId(Long memberId);
    Optional<List<Feed>> findByMemberInOrderByIdDesc(List<Member> memberList);
    Slice<Feed> findAllByOrderByIdDesc(Pageable pageable);
    Slice<Feed> findAllByMemberInOrderByIdDesc(List<Member> memberList, Pageable pageable);
}