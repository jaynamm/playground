package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor

public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/question")
    public List<QuestionDto> questionMain() {
        return questionService.questionList();
    }

    @GetMapping("/question/view")
    public QuestionDto questionRead(@RequestBody QuestionDto questionDto) {
        return questionService.readQuestion(questionDto);
    }

    @PostMapping("/question/write")
    public List<QuestionDto> questionWrite(@RequestBody QuestionDto questionDto) {
        return questionService.writeQuestion(questionDto);
    }

    @PostMapping("/question/modify")
    public List<QuestionDto> questionModify(@RequestBody QuestionDto questionDto) {
        return questionService.modifyQuestion(questionDto);
    }

    @PostMapping("/question/delete")
    public List<QuestionDto> questionDelete(@RequestBody QuestionDto questionDto) {
        return questionService.deleteQuestion(questionDto);
    }
}
