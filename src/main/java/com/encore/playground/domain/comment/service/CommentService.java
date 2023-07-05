package com.encore.playground.domain.comment.service;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.entity.Comment;
import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final FeedService feedService;
    private final MemberService memberService;

    /**
     * 피드 글번호를 이용하여 해당 글에 달린 댓글들 가져오기
     *
     * @param commentReadDto 해당 프로퍼티를 가진 Dto<br>
     *                       feedId: 피드 글번호<br>
     * @return 해당 글에 달린 댓글들
     */
    public List<CommentListDto> getCommentsInFeed(CommentReadDto commentReadDto) {
        return commentRepository.findByFeed_Id(commentReadDto.getFeedId()).get()
                .stream().map(CommentListDto::new).toList();
    }

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param memberIdDto 해당 프로퍼티를 가진 Dto<br>
     *                   userId: 멤버 테이블 id<br>
     * @return 해당 유저가 작성한 댓글 목록
     */
    public List<CommentListDto> getCommentsByUser(MemberGetMemberIdDto memberIdDto) {
        Member member = memberService.getMemberByUserid(memberIdDto.getUserid()).toEntity();
        return commentRepository.findByMemberId(member.getId()).get()
                .stream().map(CommentListDto::new).toList();
    }

    /**
     * 댓글 작성
     * @param commentWriteDto 해당 프로퍼티를 가진 Dto<br>
     *                   feedId: 피드 글번호<br>
     *                   memberId: 유저 id<br>
     *                   content: 댓글 내용
     */
    public void writeComment(CommentWriteDto commentWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        Long feedId = commentWriteDto.getFeedId();
        FeedDto feedDto = feedService.getFeed(FeedDto.builder().id(feedId).build());
        commentRepository.save(CommentDto.builder()
                        .feed(feedDto.toEntity())
                        .member(memberDto.toEntity())
                        .content(commentWriteDto.getContent())
                        .build().toEntity());
    }

    /**
     * 댓글 수정/삭제 시 해당 유저가 작성한 댓글인지 확인하는 boolean 메소드
     */
    public boolean isCommentWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        CommentDto commentDto = new CommentDto(commentRepository.findById(id).get());
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        return commentDto.getMember().getId().equals(memberDto.getId());
    }

    /**
     * 댓글 수정
     * @param commentModifyDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     *                   content: 수정할 댓글 내용<br>
     */
    public void modifyComment(CommentModifyDto commentModifyDto, MemberGetMemberIdDto memberIdDto) {
        commentRepository.save(CommentDto.builder()
                .id(commentModifyDto.getId())
                .content(commentModifyDto.getContent())
                .build().toEntity());
    }

    /**
     * 댓글 삭제
     * @param commentDeleteDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     */
    public void deleteComment(CommentDeleteDto commentDeleteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        Comment commentToDelete = commentRepository.findById(commentDeleteDto.getId()).get();
        // TODO: 삭제할 댓글이 해당 유저가 작성한 댓글인지 확인하는 로직 필요
        commentRepository.deleteById(commentDeleteDto.getId());
    }
}
