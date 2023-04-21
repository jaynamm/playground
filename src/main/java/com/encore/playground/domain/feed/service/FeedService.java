package com.encore.playground.domain.feed.service;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    /**
     * 피드 메인페이지<br>
     * 현재 모든 글을 반환하고 있으나, 추후 페이징 처리(검색 갯수 제한) 필요
     * @return 피드 피드 객체 List
     */
    public List<FeedDto> feedPage() {
        List<Feed> feedList =  feedRepository.findAll(); // 추후 페이징 처리(검색 갯수 제한) 필요
        List<FeedDto> feedDtoList = feedList.stream().map(FeedDto::new).toList();
        return feedDtoList;
    }

    /**
     * 피드 글 하나를 반환하는 메소드
     * @param feedNo: 글 번호
     * @return 피드 글 하나
     */
    public FeedDto getFeed(int feedNo) {
        Feed feed = feedRepository.findById(feedNo).get();
        return new FeedDto(feed);
    }

    /**
     * 글 작성기능
     * @param id: 글 작성자
     * @param article: 글 내용
     * @return 글 작성 이후의 피드 객체 List
     */
    public List<FeedDto> write(String id, String article) {
        FeedDto feedToWrite = FeedDto.builder()
                .userid(id)
                .uploadTime(LocalDateTime.now())
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .article(article)
                .build();
        feedRepository.save(feedToWrite.toEntity());
        return feedPage();
    }

    /**
     * 글 수정기능
     * @param feedNo: 글 번호
     * @param article: 수정할 글 내용
     * @return 글 수정 이후의 피드 객체 List
     */
    public List<FeedDto> modify(int feedNo, String article) {
        Feed feedToModify = feedRepository.findById(feedNo).get();
        FeedDto feedDto = new FeedDto(feedToModify);
        feedDto.setArticle(article);
        feedRepository.save(feedDto.toEntity());
        return feedPage();
    }

    /**
     * 글 삭제기능
     * @param feedNo: 글 번호
     * @return 글 삭제 이후의 피드 객체 List
     */
    public List<FeedDto> delete(int feedNo) {
        Feed feedToDelete = feedRepository.findById(feedNo).get();
        FeedDto feedDto = new FeedDto(feedToDelete);
        feedRepository.delete(feedDto.toEntity());
        return feedPage();
    }
}
