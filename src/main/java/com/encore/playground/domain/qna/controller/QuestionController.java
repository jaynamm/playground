package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.service.AnswerService;
import com.encore.playground.domain.qna.service.QuestionService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
@Tag(name="Question", description = "QnA 중 질문 기능 관련 API")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * 질문 전체 목록 조회
     * @return Page<QuestionDto> 질문 1페이지
     */
    @GetMapping("/question/list")
    public Page<QuestionDto> questionMain(@PageableDefault(size=10) Pageable pageable) {
        return questionService.questionList(pageable);
    }

    /**
     * 질문 상세 조회 및 해당 질문의 답변 목록 조회
     * @param id Question 테이블 id
     * @return id에 해당하는 질문 1개와 해당 질문의 답변 목록 (1개 페이지)
     */
    @GetMapping("/question/view/{id}")
    public ResponseEntity<?> viewQuestion(@PathVariable Long id, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            QuestionDto questionDto = questionService.readQuestion(id, memberIdDto);
            Map<String, Object> questionAndAnswers = new HashMap<>();
            questionAndAnswers.put("question", questionDto);
            questionAndAnswers.put("answers", answerService.getAnswerList(
                            id,
                            memberIdDto,
                            Pageable.ofSize(10)
                )
        );
        if (questionService.isQuestionWriter(id, memberIdDto)) {
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.QUESTION_WRITER_ACCESS,
                            questionAndAnswers
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.QUESTION_WRITER_ACCESS_FAILED,
                                questionAndAnswers
                        ),
                        HttpStatus.OK
                );
            }
        } else
            return null;
    }

    @PostMapping("/question/write")
    public void questionWrite(@RequestBody QuestionWriteDto questionWriteDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            questionService.writeQuestion(questionWriteDto, memberIdDto);
        }
    }

    @PostMapping("/question/modify")
    public ResponseEntity<?> questionModify(@RequestBody QuestionModifyDto questionModifyDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (questionService.isQuestionWriter(questionModifyDto.getId(), memberIdDto)) {
                questionService.modifyQuestion(questionModifyDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.QNA_MODIFY_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.QNA_MODIFY_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }


    @PostMapping("/question/delete")
    public ResponseEntity<?> questionDelete(@RequestBody QuestionGetIdDto questionIdDto, HttpServletRequest request) {
        if (request.getAttribute("AccessTokenValidation").equals("true")) {
            MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
            if (questionService.isQuestionWriter(questionIdDto.getId(), memberIdDto)) {
                questionService.deleteQuestion(questionIdDto, memberIdDto);
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.OK,
                                ResponseMessage.QNA_DELETE_SUCCESS
                        ),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        DefaultResponse.res(
                                StatusCode.UNAUTHORIZED,
                                ResponseMessage.QNA_DELETE_FAILED
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else
            return null;
    }
}
