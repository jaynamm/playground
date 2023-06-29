package com.encore.playground.domain.like.service;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.like.dto.LikeDto;
import com.encore.playground.domain.like.dto.LikeGetIdDto;
import com.encore.playground.domain.like.repository.LikeRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final FeedService feedService;
    private final MemberService memberService;

    public void like(LikeGetIdDto likeGetIdDto) {
        FeedDto feedDto = feedService.getFeed(likeGetIdDto.getFeedId());
        MemberDto memberDto = memberService.getMember(likeGetIdDto.getMemberId());
        likeRepository.save(LikeDto.builder()
                .feed(feedDto.toEntity())
                .member(memberDto.toMember())
                .likeDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    public void likeCancel(LikeGetIdDto likeGetIdDto) {
        FeedDto feedDto = feedService.getFeed(likeGetIdDto.getFeedId());
        MemberDto memberDto = memberService.getMember(likeGetIdDto.getMemberId());
        likeRepository.deleteByFeedAndMember(feedDto.toEntity(), memberDto.toMember());
    }
}
