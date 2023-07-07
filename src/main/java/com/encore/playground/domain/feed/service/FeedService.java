package com.encore.playground.domain.feed.service;

import com.encore.playground.domain.comment.repository.CommentRepository;
import com.encore.playground.domain.feed.dto.*;
import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.feed.repository.FeedRepository;
import com.encore.playground.domain.follow.service.FollowService;
import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final MemberService memberService;
    private final FollowService followService;
    private final CommentRepository commentRepository;

    /**
     * 피드에 달린 댓글 갯수를 세어서 feedListDto에 commentCount로 넣어주는 메소드
     * @param feedListDto 댓글 갯수를 표시해야 하는 피드(들)의 feedListDto
     * @return 댓글 갯수(commentCount) 값이 추가된 feedListDto
     */
    public FeedListDto countComments(FeedListDto feedListDto) {
        Long id = feedListDto.getId();
        feedListDto.setCommentCount(commentRepository.countByFeed_Id(id));
        return feedListDto;
    }

    public List<FeedListDto> feedPageAll() {
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
     * 피드 메인페이지<br>
     * 현재 모든 글을 반환하고 있으나, 추후 페이징 처리(검색 갯수 제한) 필요
     * @return 피드 피드 객체 List
     */
    public Slice<FeedListDto> feedPage(MemberGetMemberIdDto memberIdDto, Pageable pageable) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        ArrayList<MemberDto> followerListDto = new ArrayList<>(followService.getFollowingList(memberDto));
        followerListDto.add(memberDto); // 자신의 피드도 보여주기 위해 현재 사용자의 MemberDto를 추가
        Slice<Feed> feedList = feedRepository.findAllByMemberInOrderByIdDesc(
                followerListDto.stream().map(MemberDto::toEntity).toList(),
                pageable
        );
        feedList.forEach(Feed::readFeed); // 목록에 추가된 피드 조회수 증가
        Slice<FeedListDto> feedDtoList = feedList.map(FeedListDto::new).map(this::countComments);
        return feedDtoList;
    }

    /**
     * id에 해당하는 사용자가 작성한 피드 글 목록을 반환하는 메소드 (마이페이지에서 사용할 용도)
     * @param memberIdDto: jwt로부터 추출한 memberId가 들어있는 DTO
     * @return memberId에 해당하는 사용자가 작성한 피드 글 목록
     */
    public List<FeedListDto> getFeedListByMember(MemberDto memberDto) {
        List<Feed> feedList = feedRepository.findByMemberId(memberDto.getId()).get();
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

    public FeedDto getFeed(FeedGetIdDto feedGetIdDto) {
        Feed feed = feedRepository.findById(feedGetIdDto.getId()).get();
        return new FeedDto(feed);
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

    public Slice<FeedListDto> getFeedTest(Pageable pageable) {
        Slice<Feed> feedSlice = feedRepository.findAllByOrderByIdDesc(pageable);
        feedSlice.forEach(Feed::readFeed);
        return feedSlice.map(FeedListDto::new).map(this::countComments);
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
        return feedPageAll();
    }

    public void write(FeedWriteDto feedWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid((memberIdDto.getUserid()));
        feedRepository.save(FeedDto.builder()
                .member(memberDto.toEntity())
                .createdDate(LocalDateTime.now())
                .viewCount(0)
                .content(feedWriteDto.getContent())
                .build().toEntity());
    }

    /**
     * (백엔드 내부 로직) 글 상세보기를 누른 멤버가 해당 글을 작성한 멤버인지 확인하는 boolean 메소드
     * @param feedId 피드 번호
     * @param memberIdDto jwt로부터 추출한 memberId가 들어있는 DTO
     * @return 해당 글을 작성한 멤버인지 true/false
     */
    public boolean isFeedWriter(Long feedId, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        FeedDto feedDto = getFeed(FeedDto.builder().id(feedId).build());
        return memberDto.getId().equals(feedDto.getMember().getId());
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
        feedDto.setModifiedDate(LocalDateTime.now());
        feedRepository.save(feedDto.toEntity());
        return feedPageAll();
    }

    public void modify(FeedModifyDto feedModifyDto, MemberGetMemberIdDto memberIdDto) {
        FeedDto feedDto = new FeedDto(feedRepository.findById(feedModifyDto.getId()).get());
        feedDto.setContent(feedModifyDto.getContent());
        feedDto.setModifiedDate(LocalDateTime.now());
        feedRepository.save(feedDto.toEntity());
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
        return feedPageAll();
    }

    public void delete(FeedDeleteDto feedDeleteDto, MemberGetMemberIdDto memberIdDto) {
        FeedDto feedToDelete = new FeedDto(feedRepository.findById(feedDeleteDto.getId()).get());
        feedRepository.delete(feedToDelete.toEntity());
    }
}
