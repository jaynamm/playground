package com.encore.playground.domain.like.repository;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.like.entity.Like;
import com.encore.playground.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Transactional
    void deleteByFeedAndMember(Feed feed, Member member);

    // Feed의 좋아요 수
    Long countByFeed(Feed feed);

    // 사용자가 좋아요한 feed
    Long countByMember(Member member);
}
