package com.encore.playground.domain.likes.service;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.likes.dto.LikesDto;
import com.encore.playground.domain.likes.dto.LikesGetIdDto;
import com.encore.playground.domain.likes.repository.LikesRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final FeedService feedService;
    private final MemberService memberService;

    public void likes(LikesGetIdDto likesGetIdDto) {
        FeedDto feedDto = feedService.getFeed(likesGetIdDto.getFeedId());
        MemberDto memberDto = memberService.getMember(likesGetIdDto.getMemberId());
        likesRepository.save(LikesDto.builder()
                .feed(feedDto.toEntity())
                .member(memberDto.toMember())
                .likesDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    public void likesCancel(LikesGetIdDto likesGetIdDto) {
        FeedDto feedDto = feedService.getFeed(likesGetIdDto.getFeedId());
        MemberDto memberDto = memberService.getMember(likesGetIdDto.getMemberId());
        likesRepository.deleteByFeedAndMember(feedDto.toEntity(), memberDto.toMember());
    }
}
