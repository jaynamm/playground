package com.encore.playground.domain.notice.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.dto.MemberGetRoleDto;
import com.encore.playground.domain.notice.dto.NoticeDeleteDto;
import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.dto.NoticeModifyDto;
import com.encore.playground.domain.notice.dto.NoticeWriteDto;
import com.encore.playground.domain.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/notice/view/{id}")
    public NoticeDto noticeRead(@PathVariable Long id, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        return noticeService.readNotice(id, memberIdDto);
    }

    @GetMapping("/notice/write") // 글쓰기 버튼을 누를 때
    public String noticeWriteButton(HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        MemberGetRoleDto memberRoleDto = (MemberGetRoleDto) request.getAttribute("memberRoleDto");
        if (memberRoleDto.getRole().equals("ROLE_ADMIN")) {
            return "forward:/notice/write";
        } else {
            // 권한이 없다는 알림 보내주기
            return "redirect:/notice";
        }
    }

    @PostMapping("/notice/write")
    public List<NoticeDto> noticeWrite(@RequestBody NoticeWriteDto noticeWriteDto, HttpServletRequest request) {
        MemberGetRoleDto memberRoleDto = (MemberGetRoleDto) request.getAttribute("memberRoleDto");
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (memberRoleDto.getRole().equals("ROLE_ADMIN")) {
            return noticeService.writeNotice(noticeWriteDto, memberIdDto);
        } else {
            return noticeService.noticeList();
        }
    }


    @PostMapping("/notice/modify")
    public List<NoticeDto> noticeModify(@RequestBody NoticeModifyDto noticeModifyDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        return noticeService.modifyNotice(noticeModifyDto, memberIdDto);
    }

    @PostMapping("/notice/delete")
    public List<NoticeDto> noticeDelete(@RequestBody NoticeDeleteDto noticeDeleteDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        return noticeService.deleteNotice(noticeDeleteDto, memberIdDto);
    }
}
