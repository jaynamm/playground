package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    // question CRUD

    /**
     * qna 게시물을 가져온다.
     * @return List<QuestionDto>
     */

    public List<QuestionDto> questionList() {
        List<QuestionDto> questionDtoList = questionRepository.findAll().stream().map(QuestionDto::new).toList();
        return questionDtoList;
    }

    /**
     * qna 게시물을 조회한다.
     * @param questionDto
     * @return QuestionDto
     */
    public String readQuestion(QuestionDto questionDto) {
        Long id = questionDto.getId();
        return new QuestionDto(questionRepository.findById(id).get()).toString();
    }

    /**
     * qna 게시물을 작성한다.
     * @param questionDto
     * @return List<QuestionDto> (qna 메인)
     */
    public List<QuestionDto> writeQuestion(QuestionDto questionDto) {
        questionRepository.save(QuestionDto.builder()
                .title(questionDto.getTitle())
                .author(questionDto.getAuthor())
                .content(questionDto.getContent())
                .createdDate(LocalDateTime.now())
                .build().toEntity()
        );
        return questionList();
    }

    /**
     * qna 게시글을 수정한다 (update)
     * @param newQuestionDto
     * @return List<QuestionDto> (qna 메인)
     */

    public List<QuestionDto> modifyQuestion(QuestionDto newQuestionDto) {
        QuestionDto questionDto = new QuestionDto(questionRepository.findById(newQuestionDto.getId()).get());
        questionDto.setTitle(newQuestionDto.getTitle());
        questionDto.setAuthor(newQuestionDto.getAuthor());
        questionRepository.save(questionDto.toEntity());
        return questionList();
    }

    /**
     * qna 게시물 삭제
     * @param questionDto
     * @return questionList();
     */
    public List<QuestionDto> deleteQuestion(QuestionDto questionDto) {
        questionRepository.deleteById(questionDto.getId());
        return questionList();
    }

}