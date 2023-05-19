package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.service.AnswerService;
import com.encore.playground.domain.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/qna")
@RestController
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answer/list")
    private List<AnswerDto> answerList(@RequestBody Map<String, Long> getQuestionId) {
        Long questionId = getQuestionId.get("question_id");
        return answerService.answerList(questionId);
    }

    /**
     * POST - 질문 답변 생성
     * @param id
     * @param answerDTO
     * @return List<AnswerDTO>
     */
    @PostMapping("/answer/create/{id}")
    private List<AnswerDto> createAnswer(@PathVariable Long id, @RequestBody AnswerDto answerDTO) {
        System.out.println("Question ID = " + id);

        QuestionDto questionDto = questionService.readQuestion(id);

        System.out.println(questionDto);

        return answerService.create(answerDTO, questionDto);
    }
}
