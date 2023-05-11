package com.encore.playground.domain.comment.service;

import com.encore.playground.domain.comment.dto.CommentDto;
import com.encore.playground.domain.comment.dto.CommentReadDto;
import com.encore.playground.domain.comment.dto.CommentWriteDto;
import com.encore.playground.domain.comment.dto.CommentListDto;
import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
     *                       feedNo: 피드 글번호<br>
     * @return 해당 글에 달린 댓글들
     */
    public List<CommentListDto> getCommentsInFeed(CommentReadDto commentReadDto) {
        return commentRepository.findByFeed_FeedNo(commentReadDto.getFeedid()).get()
                .stream().map(CommentListDto::new).toList();
    }

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param commentReadDto 해당 프로퍼티를 가진 Dto<br>
     *                   userid: 유저 id<br>
     * @return 해당 유저가 작성한 댓글 목록
     */
    public List<CommentListDto> getCommentsByUser(CommentReadDto commentReadDto) {
        return commentRepository.findByUserid(commentReadDto.getUserid()).get()
                .stream().map(CommentListDto::new).toList();
    }

    /**
     * 댓글 작성
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   feedid: 피드 글번호<br>
     *                   userid: 유저 id<br>
     *                   article: 댓글 내용
     */
    public void writeComment(CommentWriteDto commentDto) {
        FeedDto feedDto = feedService.getFeed(FeedDto.builder().feedNo(commentDto.getFeedid()).build());
        commentRepository.save(CommentDto.builder()
                        .feed(feedDto.toEntity())
                        .userid(commentDto.getUserid())
                        .commentNo(feedDto.getCommentTotalCount())
                        .uploadTime(LocalDateTime.now())
                        .likeCount(0)
                        .article(commentDto.getArticle())
                        .build().toEntity());
        feedService.addComment(feedDto);
    }

    /**
     * 댓글 삭제
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   commentid: 댓글 번호<br>
     */
    public void deleteComment(CommentDto commentDto) {
        commentRepository.deleteById(commentDto.getCommentid());
    }

    /**
     * 댓글 수정
     * @param commentDto 해당 프로퍼티를 가진 Dto<br>
     *                   commentid: 댓글 번호<br>
     *                   article: 수정할 댓글 내용<br>
     */
    public void modifyComment(CommentDto commentDto) {
        commentRepository.save(CommentDto.builder()
                        .commentid(commentDto.getCommentid())
                        .article(commentDto.getArticle())
                        .build().toEntity());
    }
}