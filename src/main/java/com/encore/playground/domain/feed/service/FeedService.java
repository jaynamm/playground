package com.encore.playground.domain.feed.service;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
        List<Feed> feedList = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // TODO: 추후 페이징 처리(검색 갯수 제한) 필요
        List<FeedDto> feedDtoList = feedList.stream().map(FeedDto::new).toList();
        return feedDtoList;
    }

    /**
     * id에 해당하는 사용자가 작성한 피드 글 목록을 반환하는 메소드 (마이페이지에서 사용할 용도)
     * @param id 사용자 memberId
     * @return memberId에 해당하는 사용자가 작성한 피드 글 목록
     */
    public List<FeedDto> getFeedListByMember(String id) {
        List<Feed> feedList = feedRepository.findByMemberId(id).get();
        List<FeedDto> feedDtoList = feedList.stream().map(FeedDto::new).toList();
        return feedDtoList;
    }

    /**
     * 피드 글 하나를 반환하는 메소드
     * @param id: 글 번호
     * @return 피드 글 하나
     */
    public FeedDto getFeed(long id) {
        Feed feed = feedRepository.findById(id).get();
        return new FeedDto(feed);
    }

    public FeedDto getFeed(FeedDto feedDto) {
        return new FeedDto(feedRepository.findById(feedDto.getId()).get());
    }

    /**
     * 글 작성기능
     * @param memberId: 글 작성자
     * @param content: 글 내용
     * @return 글 작성 이후의 피드 객체 List
     */
    public List<FeedDto> write(String memberId, String content) {
        FeedDto feedToWrite = FeedDto.builder()
                .memberId(memberId)
                .createdDate(LocalDateTime.now())
                .likeCount(0)
                .commentCount(0)
                .commentTotalCount(0)
                .viewCount(0)
                .content(content)
                .build();
        feedRepository.save(feedToWrite.toEntity());
        return feedPage();
    }

    public List<FeedDto> write(FeedDto feedDto) {
        feedRepository.save(FeedDto.builder()
                .memberId(feedDto.getMemberId())
                .createdDate(LocalDateTime.now())
                .likeCount(0)
                .commentCount(0)
                .commentTotalCount(0)
                .viewCount(0)
                .content(feedDto.getContent())
                .build().toEntity());
        return feedPage();
    }

    /**
     * 글 수정기능
     * @param id: 글 번호
     * @param content: 수정할 글 내용
     * @return 글 수정 이후의 피드 객체 List
     */
    public List<FeedDto> modify(long id, String content) {
        Feed feedToModify = feedRepository.findById(id).get();
        FeedDto feedDto = new FeedDto(feedToModify);
        feedDto.setContent(content);
        feedRepository.save(feedDto.toEntity());
        return feedPage();
    }

    public List<FeedDto> modify(FeedDto newFeedDto) {
        FeedDto feedDto = new FeedDto(feedRepository.findById(newFeedDto.getId()).get());
        feedDto.setContent(newFeedDto.getContent());
        feedRepository.save(feedDto.toEntity());
        return feedPage();
    }

    /**
     * 글 삭제기능
     * @param id: 글 번호
     * @return 글 삭제 이후의 피드 객체 List
     */
    public List<FeedDto> delete(long id) {
        Feed feedToDelete = feedRepository.findById(id).get();
        FeedDto feedDto = new FeedDto(feedToDelete);
        feedRepository.delete(feedDto.toEntity());
        return feedPage();
    }

    public List<FeedDto> delete(FeedDto feedDto) {
        FeedDto feedToDelete = new FeedDto(feedRepository.findById(feedDto.getId()).get());
        feedRepository.delete(feedToDelete.toEntity());
        return feedPage();
    }
}
