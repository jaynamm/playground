package com.encore.playground.domain.comment.service;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.entity.Comment;
import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
     * 해당 댓글에 수정/삭제 버튼 보여줄지 여부를 체크하기
     * @param commentDto 가져온 댓글
     * @param memberIdDto 현재 로그인한 사용자의 userId
     * @return isEditable이 적용된 CommentListDto
     */
    public CommentListDto isCommentWriter (CommentDto commentDto, MemberGetMemberIdDto memberIdDto) {
        CommentListDto commentListDto = new CommentListDto(commentDto.toEntity());
        // 댓글 작성자와 로그인한 사용자가 같은지 확인한다.
        if (commentDto.getMember().getUserid().equals(memberIdDto.getUserid())) {
            // 같으면 isEditable을 true로 만든다.
            commentListDto.setEditable(true);
        }
        return commentListDto;
    }

    /**
     * 댓글 수정/삭제 시 해당 유저가 작성한 댓글인지 확인하는 boolean 메소드
     */
    public boolean isCommentWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        CommentDto commentDto = new CommentDto(commentRepository.findById(id).get());
        return commentDto.getMember().getUserid().equals(memberIdDto.getUserid());
    }


    /**
     * 피드 글번호를 이용하여 해당 글에 달린 댓글들 가져오기
     *
     * @param commentReadDto 해당 프로퍼티를 가진 Dto<br>
     *                       feedId: 피드 글번호<br>
     * @return 해당 글에 달린 댓글들
     */
    public Slice<CommentListDto> getCommentsInFeed(CommentReadDto commentReadDto, MemberGetMemberIdDto memberIdDto, Pageable pageable) {
        // 리포지토리에서 feedId에 해당하는 댓글을 가져온다
        Slice<Comment> comments = commentRepository.findAllByFeed_IdOrderById(commentReadDto.getFeedId(), pageable);
        // 댓글들 중 로그인한 사용자가 작성한 댓글이 있는지 확인한다. 있으면 플래그를 true로 만든다.
        Slice<CommentListDto> commentList = comments.map(CommentDto::new).map(CommentDto -> this.isCommentWriter(CommentDto, memberIdDto));
        // 댓글들을 CommentListDto로 변환한다.
        return commentList;
    }

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param memberDto 해당 프로퍼티를 가진 Dto<br>
     *                   Id: 멤버 테이블 pk<br>
     * @return 해당 유저가 작성한 댓글 목록
     */
    public List<CommentListDto> getCommentListByMember(MemberDto memberDto) {
        return commentRepository.findByMemberId(memberDto.getId()).get()
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
     * 댓글 수정
     * @param commentModifyDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     *                   content: 수정할 댓글 내용<br>
     */
    public void modifyComment(CommentModifyDto commentModifyDto, MemberGetMemberIdDto memberIdDto) {
        CommentDto commentDto = new CommentDto(commentRepository.findById(commentModifyDto.getId()).get());
        commentDto.setContent(commentModifyDto.getContent());
        commentRepository.save(commentDto.toEntity());
    }

    /**
     * 댓글 삭제
     * @param commentDeleteDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     */
    public void deleteComment(CommentDeleteDto commentDeleteDto, MemberGetMemberIdDto memberIdDto) {
        commentRepository.deleteById(commentDeleteDto.getId());
    }
}
