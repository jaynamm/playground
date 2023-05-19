package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<AnswerDto> answerList(Long questionId) {
        return answerRepository.findAnswerByQuestion_id(questionId).stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> create(AnswerDto answerDTO, QuestionDto questionDto) {
        Answer answer = Answer.builder()
                .content(answerDTO.getContent())
                .memberId(answerDTO.getMemberId())
                .createdDate(LocalDateTime.now())
                .question(questionDto.toEntity())
                .build();

        answerRepository.save(answer);

        return answerList(answer.getId());
    }
}
