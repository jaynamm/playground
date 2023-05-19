package com.encore.playground.domain.notice.service;

import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // notice CRUD


    /**
     * 공지사항 게시물을 가져온다.
     * @return List<NoticeDto>
     */
    public List<NoticeDto> noticeList() {
            List<NoticeDto> noticeDtoList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(NoticeDto::new).toList();
            return noticeDtoList;
    }

    /**
     * 공지사항 게시물을 조회한다.
     * @param noticeDto
     * @return NoticeDto
     */
    public NoticeDto readNotice(Long noticeId) {
        return new NoticeDto(noticeRepository.findById(noticeId).get());
    }

    /**
     * Create
     * 공지사항 게시물을 작성한다.
     * @param noticeDto
     * @return List<NoticeDto> (공지사항 메인)
     */

    public List<NoticeDto> writeNotice(NoticeDto noticeDto) {
        noticeRepository.save(NoticeDto.builder()
                .title(noticeDto.getTitle())
                .memberId(noticeDto.getMemberId())
                .content(noticeDto.getContent())
                .createdDate(LocalDateTime.now())
                .viewCount(0)
                .build().toEntity()
        );
        return noticeList();
    }

    /**
     * Update
     * @param newNoticeDto
     * @return List<NoticeDto> (공지사항 메인)
     */

    public List<NoticeDto> modifyNotice(NoticeDto newNoticeDto) {
        NoticeDto noticeDto = new NoticeDto(noticeRepository.findById(newNoticeDto.getId()).get());
        noticeDto.setTitle(newNoticeDto.getTitle());
        noticeDto.setContent(newNoticeDto.getContent());
        noticeRepository.save(noticeDto.toEntity());
        return noticeList();

    }

    /**
     * Delete
     * @param noticeId
     * @return List<NoticeDto> (공지사항 메인)
     */

    public List<NoticeDto> deleteNotice(NoticeDto noticeDto) {
        noticeRepository.deleteById(noticeDto.getId());
        return noticeList();
    }

}
