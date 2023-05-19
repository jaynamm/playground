package com.encore.playground.domain.notice.controller;

import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/notice")
    public List<NoticeDto> noticeMain() {
        return noticeService.noticeList();
    }

    @PostMapping("/notice/view/{id}")
    public NoticeDto noticeRead(@PathVariable Long id) {
        return noticeService.readNotice(id);
    }

    @PostMapping("/notice/write")
    public List<NoticeDto> noticeWrite(@RequestBody NoticeDto noticeDto) {
            return noticeService.writeNotice(noticeDto);
    }


    @PostMapping("/notice/modify")
    public List<NoticeDto> noticeModify(@RequestBody NoticeDto noticeDto) {
        return noticeService.modifyNotice(noticeDto);
    }

    @PostMapping("/notice/delete")
    public List<NoticeDto> noticeDelete(@RequestBody NoticeDto noticeDto) {
        return noticeService.deleteNotice(noticeDto);
    }
}
