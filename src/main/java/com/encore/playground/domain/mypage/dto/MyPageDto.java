package com.encore.playground.domain.mypage.dto;

import com.encore.playground.domain.comment.dto.CommentDto;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.follow.dto.FollowMyPageDto;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDto {
    private MemberDto memberDto;
    private FollowMyPageDto followMyPageDto;
    private List<FeedDto> myPageFeedDtoList;
    private List<CommentDto> myPageCommentDtoList;
    private List<QuestionDto> myPageQuestionDtoList;
    private List<AnswerDto> myPageAnswerDtoList;




}
