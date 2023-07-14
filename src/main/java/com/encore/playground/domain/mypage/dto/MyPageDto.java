package com.encore.playground.domain.mypage.dto;

import com.encore.playground.domain.comment.dto.CommentListDto;
import com.encore.playground.domain.feed.dto.FeedListDto;
import com.encore.playground.domain.follow.dto.FollowMyPageDto;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberMyPageDto;
import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDto {
    private MemberMyPageDto memberMyPageDto;
    private FollowMyPageDto followMyPageDto;
    private Slice<FeedListDto> myPageFeedDtoList;
    private Slice<CommentListDto> myPageCommentDtoList;
    private Page<QuestionDto> myPageQuestionDtoList;
    private Page<AnswerDto> myPageAnswerDtoList;
}
