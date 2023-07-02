package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public List<AnswerDto> answerList(Long questionId) {
        return answerRepository.findAnswerByQuestion_Id(questionId).get().stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> getAnswerListByMember(String memberId) {
        return answerRepository.findByMemberId(memberId).get().stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> create(AnswerDto answerDTO, Long questionId) {
        QuestionDto questionDto = questionService.readQuestion(questionId);
        answerDTO = AnswerDto.builder()
                .content(answerDTO.getContent())
                .member(answerDTO.getMember())
                .createdDate(LocalDateTime.now())
                .question(questionDto.toEntity())
                .build();

        answerRepository.save(answerDTO.toEntity());

        return answerList(questionId);
    }
}
