package com.encore.playground.domain.notice.service;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.notice.dto.NoticeGetIdDto;
import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.dto.NoticeModifyDto;
import com.encore.playground.domain.notice.dto.NoticeWriteDto;
import com.encore.playground.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;


    // notice CRUD


    /**
     * 공지사항 게시물을 가져온다.
     * @return List<NoticeDto>
     */
    public Page<NoticeDto> noticeList(Pageable pageable) {
            Page<NoticeDto> noticeDtoList = noticeRepository.findAllByOrderByIdDesc(pageable).map(NoticeDto::new);
            return noticeDtoList;
    }

    public boolean isNoticeWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        return memberIdDto.getUserid().equals(noticeRepository.findById(id).get().getMember().getUserid());
    }

    /**
     * 공지사항 게시물을 조회한다.
     * @param noticeId, memberIdDto
     * @return NoticeDto
     */
    public NoticeDto readNotice(Long noticeId, MemberGetMemberIdDto memberIdDto) {
        return new NoticeDto(noticeRepository.findById(noticeId).get());
    }

    /**
     * Create
     * 공지사항 게시물을 작성한다.
     * @param noticeWriteDto, memberIdDto
     * @return void
     */

    public void writeNotice(NoticeWriteDto noticeWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        noticeRepository.save(NoticeDto.builder()
                .title(noticeWriteDto.getTitle())
                .member(memberDto.toEntity())
                .content(noticeWriteDto.getContent())
                .createdDate(LocalDateTime.now())
                .viewCount(0)
                .build().toEntity()
        );
//        return noticeList();
    }

    /**
     * Update
     * @param newNoticeDto, memberIdDto
     * @return void
     */

    public void modifyNotice(NoticeModifyDto newNoticeDto, MemberGetMemberIdDto memberIdDto) {
        NoticeDto noticeDto = new NoticeDto(noticeRepository.findById(newNoticeDto.getId()).get());
        noticeDto.setTitle(newNoticeDto.getTitle());
        noticeDto.setContent(newNoticeDto.getContent());
        noticeRepository.save(noticeDto.toEntity());

    }

    /**
     * Delete
     * @param noticeGetIdDto, memberIdDto
     * @return void
     */

    public void deleteNotice(NoticeGetIdDto noticeGetIdDto, MemberGetMemberIdDto memberIdDto) {
        noticeRepository.deleteById(noticeGetIdDto.getId());
    }

}
