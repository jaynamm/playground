package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.service.AnswerService;
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

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/qna")
@RestController
public class AnswerController {
    private final AnswerService answerService;

    /**
     * POST - id에 해당하는 질문의 답변 목록 조회
     * @param questionGetIdDto - question_id
     * @return  List<AnswerDTO>
     */
    @PostMapping("/answer/list")
    private Page<AnswerListDto> answerList(@RequestBody QuestionGetIdDto questionGetIdDto, HttpServletRequest request, @PageableDefault(size=10) Pageable pageable) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            Long questionId = questionGetIdDto.getId();
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            return answerService.getAnswerList(questionId, memberIdDto, pageable);
        } else
            return null;
    }


    /**
     * POST - 질문 답변 생성
     * @param answerWriteDto
     * @param request
     * @return void
     */
    @PostMapping("/answer/write")
    private void writeAnswer(@RequestBody AnswerWriteDto answerWriteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            answerService.createAnswer(answerWriteDto, memberIdDto);
        }
    }

    /**
     * Post - 질문에 대한 답변을 수정
     * @param answerModifyDto
     * @param request
     * @return ResponseEntity
     */
    @PostMapping("/answer/modify")
    private ResponseEntity<?> modifyAnswer(@RequestBody AnswerModifyDto answerModifyDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (answerService.isAnswerWriter(answerModifyDto.getId(), memberIdDto)) {
                answerService.modifyAnswer(answerModifyDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.ANSWER_MODIFY_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.ANSWER_MODIFY_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }

    /**
     * Post - 질문에 대한 답변 삭제
     * @param answerDeleteDto
     * @param request
     * @return ResponseEntity
     */

    @PostMapping("/answer/delete")
    private ResponseEntity<?> deleteAnswer(@RequestBody AnswerDeleteDto answerDeleteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (answerService.isAnswerWriter(answerDeleteDto.getId(), memberIdDto)) {
                answerService.deleteAnswer(answerDeleteDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.ANSWER_DELETE_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.ANSWER_DELETE_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }
}
