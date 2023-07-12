package com.encore.playground.domain.notice.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.dto.MemberGetRoleDto;
import com.encore.playground.domain.notice.dto.NoticeDto;
import com.encore.playground.domain.notice.dto.NoticeGetIdDto;
import com.encore.playground.domain.notice.dto.NoticeModifyDto;
import com.encore.playground.domain.notice.dto.NoticeWriteDto;
import com.encore.playground.domain.notice.service.NoticeService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public ResponseEntity<?> noticeMain(HttpServletRequest request, @PageableDefault(size=10) Pageable pageable) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        MemberGetRoleDto memberRoleDto = (MemberGetRoleDto) request.getAttribute("memberRoleDto");
        Page<NoticeDto> noticeDtoList = noticeService.noticeList(pageable);
            if (memberRoleDto.getRole().equals("ROLE_ADMIN")) {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_ADMIN_ACCESS,
                                noticeDtoList
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_USER_ACCESS,
                                noticeDtoList
                        ),
                        HttpStatus.OK
                );
            }
        } else
            return null;
    }
    @GetMapping("/notice/view/{id}")
    public ResponseEntity<?> noticeRead(@PathVariable Long id, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (noticeService.isNoticeWriter(id, memberIdDto)) {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_WRITER_ACCESS,
                                noticeService.readNotice(id, memberIdDto)
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_WRITER_ACCESS_FAILED,
                                noticeService.readNotice(id, memberIdDto)
                        ),
                        HttpStatus.OK
                );
            }
        } else
            return null;
    }


    @PostMapping("/notice/write")
    public ResponseEntity<?> noticeWrite(@RequestBody NoticeWriteDto noticeWriteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetRoleDto memberRoleDto = (MemberGetRoleDto) request.getAttribute("memberRoleDto");
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (memberRoleDto.getRole().equals("ROLE_ADMIN")) {
                noticeService.writeNotice(noticeWriteDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_ADMIN_ACCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.NOTICE_USER_ACCESS,
                                noticeService.noticeList(Pageable.ofSize(10))
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }


    @PostMapping("/notice/modify")
    public ResponseEntity<?> noticeModify(@RequestBody NoticeModifyDto noticeModifyDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (noticeService.isNoticeWriter(noticeModifyDto.getId(), memberIdDto)) {
                noticeService.modifyNotice(noticeModifyDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_WRITER_ACCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.NOTICE_WRITER_ACCESS_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }

        } else
            return null;
    }

    @PostMapping("/notice/delete")
    public ResponseEntity<?> noticeDelete(@RequestBody NoticeGetIdDto noticeGetIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (noticeService.isNoticeWriter(noticeGetIdDto.getId(), memberIdDto)) {
                noticeService.deleteNotice(noticeGetIdDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.NOTICE_DELETE_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.NOTICE_DELETE_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }
}
