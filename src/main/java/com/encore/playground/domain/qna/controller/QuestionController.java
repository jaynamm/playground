package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.QuestionDeleteDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.dto.QuestionModifyDto;
import com.encore.playground.domain.qna.dto.QuestionWriteDto;
import com.encore.playground.domain.qna.service.QuestionService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
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

public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question/list")
    public List<QuestionDto> questionMain() {
        System.out.println("[/api/qna/question] ::: 질문 목록 가져가기");

        return questionService.questionList();
    }

    @GetMapping("/question/list/{memberId}")
    public List<QuestionDto> questionList(@PathVariable String memberId) {
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

    @PostMapping("/question/modify")
    public ResponseEntity<?> questionModify(@RequestBody QuestionModifyDto questionModifyDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        questionService.modifyQuestion(questionModifyDto, memberIdDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_MODIFY
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/question/delete")
    public ResponseEntity<?> questionDelete(@RequestBody QuestionDeleteDto questionDeleteDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        questionService.deleteQuestion(questionDeleteDto, memberIdDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_DELETE
                ),
                HttpStatus.OK
        );
    }
}