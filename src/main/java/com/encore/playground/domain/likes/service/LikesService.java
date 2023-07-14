package com.encore.playground.domain.likes.service;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.dto.FeedGetIdDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.likes.dto.LikesDto;
import com.encore.playground.domain.likes.dto.LikesGetIdDto;
import com.encore.playground.domain.likes.repository.LikesRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
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

    public void likes(LikesGetIdDto likesGetIdDto, MemberGetMemberIdDto memberIdDto) {
        FeedDto feedDto = feedService.getFeed(FeedGetIdDto.builder().id(likesGetIdDto.getFeedId()).build());
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        if (likesRepository.existsByFeed_IdAndMember_Id(feedDto.getId(), memberDto.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 누른 피드입니다.");
        }
        likesRepository.save(LikesDto.builder()
                .feed(feedDto.toEntity())
                .member(memberDto.toEntity())
                .createdDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    public void likesCancel(LikesGetIdDto likesGetIdDto, MemberGetMemberIdDto memberIdDto) {
        FeedDto feedDto = feedService.getFeed(FeedGetIdDto.builder().id(likesGetIdDto.getFeedId()).build());
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        if (!likesRepository.existsByFeed_IdAndMember_Id(feedDto.getId(), memberDto.getId())) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 피드입니다.");
        }
        likesRepository.deleteByFeedAndMember(feedDto.toEntity(), memberDto.toEntity());
    }
}
