package com.encore.playground.domain.mypage.service;

import com.encore.playground.domain.comment.dto.CommentListDto;
import com.encore.playground.domain.comment.service.CommentService;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.dto.FeedListDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.follow.dto.FollowMyPageDto;
import com.encore.playground.domain.follow.service.FollowService;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.dto.MemberMyPageDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.mypage.dto.MyPageDto;
import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.service.AnswerService;
import com.encore.playground.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final FollowService followService;
    private final FeedService feedService;
    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;


    public MyPageDto myPage(MemberGetMemberIdDto memberIdDto) {
        // 사용자의 정보를 가져온다.
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());

        MemberMyPageDto memberMyPageDto = memberService.getMemberMyPageDtoByUserid(memberDto);
        // 사용자의 follow 정보를 가져온다. (팔로잉 수, 팔로워 수, 팔로잉 리스트. 팔로워 리스트)
        FollowMyPageDto followDto = followService.getFollowMyPageDto(memberDto);
        // 사용자가 작성한 feed를 가져온다.
        List<FeedListDto> feedListDto = feedService.getFeedListByMember(memberDto);
        // 사용자가 작성한 comment를 가져온다.
        List<CommentListDto> commentListDto = commentService.getCommentListByMember(memberDto);
        // 사용자가 작성한 question을 가져온다.
        List<QuestionDto> questionListDto = questionService.getQuestionListByMember(memberDto);
        // 사용자가 작성한 answer를 가져온다.
        List<AnswerDto> answerListDto = answerService.getAnswerListByMember(memberDto);
        // MyPageDto에 담아서 build한다.
        return MyPageDto.builder()
                .memberMyPageDto(memberMyPageDto)
                .followMyPageDto(followDto)
                .myPageFeedDtoList(feedListDto)
                .myPageCommentDtoList(commentListDto)
                .myPageQuestionDtoList(questionListDto)
                .myPageAnswerDtoList(answerListDto)
                .build();
    }
}
