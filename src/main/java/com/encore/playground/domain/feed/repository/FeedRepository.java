package com.encore.playground.domain.feed.repository;

import com.encore.playground.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 피드 글을 저장하는 리포지토리
 */
public interface FeedRepository extends JpaRepository<Feed, Integer> {}