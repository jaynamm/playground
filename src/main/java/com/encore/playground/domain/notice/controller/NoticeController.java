package com.encore.playground.domain.notice.controller;

import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/notice")
    public List<NoticeDto> noticeMain() {
        return noticeService.noticeList();
    }

    @GetMapping("/notice/view")
    public NoticeDto noticeRead(@RequestBody NoticeDto noticeDto) {
        return noticeService.readNotice(noticeDto);
    }

    @PostMapping("/notice/write")
    public List<NoticeDto> noticeWrite(@RequestBody NoticeDto noticeDto) {
            return noticeService.writeNotice(noticeDto);
    }


    @PostMapping("/notice/modify")
    public List<NoticeDto> noticeModify(@RequestBody NoticeDto noticeDto) {
        return noticeService.modifyNotice(noticeDto);
    }

    @GetMapping("/notice/delete")
    public List<NoticeDto> noticeDelete(@RequestBody NoticeDto noticeDto) {
        return noticeService.deleteNotice(noticeDto);
    }
}
