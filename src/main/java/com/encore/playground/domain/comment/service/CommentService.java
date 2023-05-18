package com.encore.playground.domain.comment.service;

import com.encore.playground.domain.comment.dto.*;
import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
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
     * @param commentReadDto 해당 프로퍼티를 가진 Dto<br>
     *                   memberId: 유저 id<br>
     * @return 해당 유저가 작성한 댓글 목록
     */
    public List<CommentListDto> getCommentsByUser(CommentReadDto commentReadDto) {
        return commentRepository.findByMemberId(commentReadDto.getMemberId()).get()
                .stream().map(CommentListDto::new).toList();
    }

    /**
     * 댓글 작성
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   feedId: 피드 글번호<br>
     *                   memberId: 유저 id<br>
     *                   content: 댓글 내용
     */
    public void writeComment(CommentWriteDto commentDto) {
        FeedDto feedDto = feedService.getFeed(FeedDto.builder().id(commentDto.getFeedId()).build());
        commentRepository.save(CommentDto.builder()
                        .feed(feedDto.toEntity())
                        .memberId(commentDto.getMemberId())
//                        .commentNo(feedDto.getCommentTotalCount())
                        .likeCount(0)
                        .content(commentDto.getContent())
                        .build().toEntity());
        feedService.addComment(feedDto);
    }

    /**
     * 댓글 수정
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     *                   content: 수정할 댓글 내용<br>
     */
    public void modifyComment(CommentModifyDto commentDto) {
        commentRepository.save(CommentDto.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .build().toEntity());
    }

    /**
     * 댓글 삭제
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   id: 댓글 번호<br>
     */
    public void deleteComment(CommentDeleteDto commentDto) {
        CommentDeleteDto commentDeleteDto = new CommentDeleteDto(commentRepository.findById(commentDto.getId()).get());
        FeedDto feedDto = feedService.getFeed(FeedDto.builder().id(commentDeleteDto.getFeedid()).build());
        commentRepository.deleteById(commentDto.getId());
        feedService.deleteComment(feedDto);
    }
}