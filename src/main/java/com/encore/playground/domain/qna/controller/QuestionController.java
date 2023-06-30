package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.service.QuestionService;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
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
    public List<QuestionDto> questionList(@PathVariable String memberId) {
        return questionService.getQuestionListByMember(memberId);
    }

    @GetMapping("/question/view/{id}")
    public ResponseEntity<?> viewQuestion(@PathVariable Long id) {
        QuestionDto questionDto = questionService.readQuestion(id);

        Map<String, QuestionDto> questionDtoMap = new HashMap<>();
        questionDtoMap.put("question", questionDto);

        return new ResponseEntity(questionDtoMap, HttpStatus.OK);
    }

    @PostMapping("/question/write")
    public ResponseEntity<?> questionWrite(@RequestBody QuestionDto questionDto) {
        System.out.println(questionDto.getTitle());
        System.out.println(questionDto.getMemberId());
        System.out.println(questionDto.getContent());

        questionService.writeQuestion(questionDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_WRITE_SUCCESS
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/question/modify")
    public ResponseEntity<?> questionModify(@RequestBody QuestionDto questionDto) {
        questionService.modifyQuestion(questionDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_MODIFY
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/question/delete")
    public ResponseEntity<?> questionDelete(@RequestBody QuestionDto questionDto) {
        questionService.deleteQuestion(questionDto);

        return new ResponseEntity(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.QNA_DELETE
                ),
                HttpStatus.OK
        );
    }
}