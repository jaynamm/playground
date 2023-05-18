package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import com.encore.playground.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public List<AnswerDto> answerList(Long questionId) {
        return answerRepository.findByQuestionId(questionId).stream().map(AnswerDto::new).toList();
    }

//    public List<AnswerDto> create(AnswerDto answerDTO) {
//        Answer answer = Answer.builder()
//                .content(answerDTO.getContent())
//                .author(answerDTO.getAuthor())
//                .createdDate(LocalDateTime.now())
//                .question(answerDTO.getQuestion())
//                .build();
//
//        answerRepository.save(answer);
//
//        return answerList();
//    }
}