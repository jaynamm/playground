package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.service.QuestionService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
@Tag(name="Question", description = "QnA 중 질문 기능 관련 API")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question/list")
    public List<QuestionDto> questionMain() {
        System.out.println("[/api/qna/question] ::: 질문 목록 가져가기");

        return questionService.questionList();
    }

    @GetMapping("/question/list/{memberId}")
    public List<QuestionDto> questionList(@PathVariable Long memberId) {
        return questionService.getQuestionListByMember(memberId);
    }

    @GetMapping("/question/view/{id}")
    public ResponseEntity<?> viewQuestion(@PathVariable Long id, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        QuestionDto questionDto = questionService.readQuestion(id, memberIdDto);
        Map<String, QuestionDto> questionDtoMap = new HashMap<>();
        questionDtoMap.put("question", questionDto);

        return new ResponseEntity(questionDtoMap, HttpStatus.OK);
    }

    @PostMapping("/question/write")
    public ResponseEntity<?> questionWrite(@RequestBody QuestionWriteDto questionWriteDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        questionService.writeQuestion(questionWriteDto, memberIdDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_WRITE_SUCCESS
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/question/modify")
    public ResponseEntity<?> questionModifyButton(@RequestBody QuestionGetIdDto questionIdDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (questionService.isQuestionWriter(questionIdDto.getId(), memberIdDto)) {
            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.QNA_MODIFY_ACCESS
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.QNA_MODIFY_FAILED
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }


    @PostMapping("/question/modify")
    public ResponseEntity<?> questionModify(@RequestBody QuestionModifyDto questionModifyDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (questionService.isQuestionWriter(questionModifyDto.getId(), memberIdDto)) {
            questionService.modifyQuestion(questionModifyDto, memberIdDto);

            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.QNA_MODIFY
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.QNA_MODIFY_FAILED
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }


    @PostMapping("/question/delete")
    public ResponseEntity<?> questionDelete(@RequestBody QuestionGetIdDto questionIdDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (questionService.isQuestionWriter(questionIdDto.getId(), memberIdDto)) {
            questionService.deleteQuestion(questionIdDto, memberIdDto);

            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.OK,
                            ResponseMessage.QNA_DELETE
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    DefaultResponse.res(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.QNA_DELETE_FAILED
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}