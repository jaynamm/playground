package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.qna.dto.AnswerDTO;
import com.encore.playground.domain.qna.service.AnswerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/qna")
@RestController
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/answer/create")
    private List<AnswerDTO> createAnswer(@RequestBody AnswerDTO answerDTO) {
        return answerService.create(answerDTO);
    }
}