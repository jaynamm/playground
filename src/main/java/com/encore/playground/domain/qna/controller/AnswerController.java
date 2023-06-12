package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.service.AnswerService;
import lombok.RequiredArgsConstructor;
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
     * @param getQuestionId - question_id
     * @return  List<AnswerDTO>
     */
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
//    private void createAnswer(@PathVariable Long id, @RequestBody AnswerWriteDto answerDTO) {
//        answerService.create(answerDTO, id);
        return answerService.create(answerDTO, id);
    }
}
