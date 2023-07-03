package com.encore.playground.domain.feed.service;

import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.*;
import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.feed.repository.FeedRepository;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.service.MemberService;
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
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    /**
     * 피드 갯수
     */
    public FeedListDto countComments(FeedListDto feedListDto) {
        Long id = feedListDto.getId();
        feedListDto.setCommentCount(commentRepository.countByFeed_Id(id));
        return feedListDto;
    }

    /**
     * 피드 메인페이지<br>
     * 현재 모든 글을 반환하고 있으나, 추후 페이징 처리(검색 갯수 제한) 필요
     * @return 피드 피드 객체 List
     */
    public List<FeedListDto> feedPage() {
        List<Feed> feedList = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // TODO: 추후 페이징 처리(검색 갯수 제한) 필요
        List<FeedListDto> feedDtoList = feedList.stream()
                // Feed Entity를 FeedListDto로 변환
                .map(FeedListDto::new)
                // FeedListDto들에 commentCount 값 입력
                .map(this::countComments)
                .toList();
        return feedDtoList;
    }

    /**
     * id에 해당하는 사용자가 작성한 피드 글 목록을 반환하는 메소드 (마이페이지에서 사용할 용도)
     * @param id 사용자 memberId
     * @return memberId에 해당하는 사용자가 작성한 피드 글 목록
     */
    public List<FeedListDto> getFeedListByMember(String id) {
        List<Feed> feedList = feedRepository.findByMemberId(id).get();
        List<FeedListDto> feedDtoList = feedList.stream().map(FeedListDto::new).map(this::countComments).toList();
        return feedDtoList;
    }

    /**
     * 글 번호를 사용하여 FeedListDto를 반환하는 메소드(Controller API에 사용)
     * @param id: 글 번호
     * @return FeedListDto
     */
    public FeedListDto getFeed(Long id) {
        Feed feed = feedRepository.findById(id).get();
        return countComments(new FeedListDto(feed));
    }

    /**
     * FeedDto를 사용하여 FeedDto를 반환하는 메소드(백엔드 내부 로직에 사용)
     * @param feedDto
     * @return FeedDto
     */
    public FeedDto getFeed(FeedDto feedDto) {
        Feed feed = feedRepository.findById(feedDto.getId()).get();
        return new FeedDto(feed);
    }

    /**
     * 글 작성기능
     * @param memberId: 글 작성자
     * @param content: 글 내용
     * @return 글 작성 이후의 피드 객체 List
     */
    public List<FeedListDto> write(String memberId, String content) {
        MemberDto memberDto = memberService.getMemberByUserid(memberId);
        FeedDto feedToWrite = FeedDto.builder()
                .member(memberDto.toEntity())
                .createdDate(LocalDateTime.now())
                .viewCount(0)
                .content(content)
                .build();
        feedRepository.save(feedToWrite.toEntity());
        return feedPage();
    }

    public List<FeedListDto> write(FeedWriteDto feedWriteDto) {
        // TODO: jwt에서 memberId를 추출하여 사용하도록 수정 필요
        String memberId = "qwer";
        MemberDto memberDto = memberService.getMemberByUserid(memberId);
        feedRepository.save(FeedDto.builder()
                .member(memberDto.toEntity())
                .createdDate(LocalDateTime.now())
                .viewCount(0)
                .content(feedWriteDto.getContent())
                .build().toEntity());
        return feedPage();
    }

    /**
     * 글 수정기능
     * @param id: 글 번호
     * @param content: 수정할 글 내용
     * @return 글 수정 이후의 피드 객체 List
     */
    public List<FeedListDto> modify(Long id, String content) {
        Feed feedToModify = feedRepository.findById(id).get();
        FeedDto feedDto = new FeedDto(feedToModify);
        feedDto.setContent(content);
        feedRepository.save(feedDto.toEntity());
        return feedPage();
    }

    public List<FeedListDto> modify(FeedModifyDto feedModifyDto) {
        FeedDto feedDto = new FeedDto(feedRepository.findById(feedModifyDto.getId()).get());
        feedDto.setContent(feedModifyDto.getContent());
        feedRepository.save(feedDto.toEntity());
        return feedPage();
    }

    /**
     * 글 삭제기능
     * @param id: 글 번호
     * @return 글 삭제 이후의 피드 객체 List
     */
    public List<FeedListDto> delete(long id) {
        Feed feedToDelete = feedRepository.findById(id).get();
        FeedDto feedDto = new FeedDto(feedToDelete);
        feedRepository.delete(feedDto.toEntity());
        return feedPage();
    }

    public List<FeedListDto> delete(FeedDeleteDto feedDeleteDto) {
        FeedDto feedToDelete = new FeedDto(feedRepository.findById(feedDeleteDto.getId()).get());
        feedRepository.delete(feedToDelete.toEntity());
        return feedPage();
    }
}
