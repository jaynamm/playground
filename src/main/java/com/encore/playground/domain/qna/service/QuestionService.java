package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.qna.dto.AnswerDto;
import com.encore.playground.domain.qna.dto.QuestionDto;
import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import com.encore.playground.domain.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;

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
     *
     * 조회하고자 하는 question객체의 id값을 가져와서
     * 해당 id를 가지고 있는 answer객체를 반환하고
     * 이를 List에 담아서 questionDto에 담는다.
     */


    public QuestionDto readQuestion(QuestionDto questionDto) {
        Long id = questionDto.getId();
        QuestionDto readQuestionDto = new QuestionDto(questionRepository.findById(id).get());
        List<AnswerDto> answerDtoList = answerRepository.findByQuestionId(id).stream().map(AnswerDto::new).toList();
        QuestionDto newQuestionDto = QuestionDto.builder()
                .id(id)
                .title(readQuestionDto.getTitle())
                .author(readQuestionDto.getAuthor())
                .content(readQuestionDto.getContent())
                .createdDate(readQuestionDto.getCreatedDate())
                .answerList(answerDtoList.toString())
                .build();
        return newQuestionDto;
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
