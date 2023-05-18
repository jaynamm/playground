package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.service.AnswerService;
import com.encore.playground.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor

public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/question")
    public List<QuestionDto> questionMain() {
        return questionService.questionList();
    }

    @PostMapping("/question/view")
    public Map questionRead(@RequestBody QuestionDto questionDto) {
        String question = questionService.readQuestion(questionDto);

        Map<String, > getQuestion = new HashMap<>();
        getQuestion.put("quetion", question);
        getQuestion.put("answer", answer);

        return getQuestion;
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