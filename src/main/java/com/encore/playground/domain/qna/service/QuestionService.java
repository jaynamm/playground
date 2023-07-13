package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.entity.Question;
import com.encore.playground.domain.qna.repository.QuestionRepository;
import com.encore.playground.global.exception.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    // question CRUD

    /**
     * qna 게시물을 가져온다.
     * @return List<QuestionDto>
     */
    public Page<QuestionDto> questionList(Pageable pageable) {
        Page<QuestionDto> questionDtoList = questionRepository.findAllByOrderByIdDesc(pageable).map(QuestionDto::new);
        return questionDtoList;
    }

    /**
     * id에 해당하는 글의 작성자와 로그인한 사용자가 같은지 확인한다.
     * @param id Question 테이블 id
     * @return 로그인한 사용자가 쓴 글인지 true/false 
     */
    public boolean isQuestionWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        return memberIdDto.getUserid().equals(questionRepository.findById(id).get().getMember().getUserid());
    }

    /**
     * id에 해당하는 작성자가 작성한 qna 게시물 목록을 가져온다.
     * @param memberDto
     * @return List<QuestionDto>
     */
    public Page<QuestionDto> getQuestionListByMember(MemberDto memberDto, Pageable pageable) {
        Page<QuestionDto> questionDtoList = questionRepository.findByMemberIdOrderByIdDesc(memberDto.getId(), pageable).map(QuestionDto::new);
        return questionDtoList;
    }

    /**
     * qna 게시물을 조회한다.
     * @param questionId
     * @return QuestionDto
     */
    public QuestionDto readQuestion(Long questionId, MemberGetMemberIdDto memberIdDto) {
        Optional<Question> questionDto = this.questionRepository.findById(questionId);

        if (questionDto.isPresent()) {
            return new QuestionDto(questionDto.get());
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    /**
     * qna 게시물을 작성한다.
     * @param questionWriteDto
     * @param memberIdDto
     * @return void
     */
    public void writeQuestion(QuestionWriteDto questionWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        questionRepository.save(QuestionDto.builder()
                .title(questionWriteDto.getTitle())
                .member(memberDto.toEntity())
                .content(questionWriteDto.getContent())
                .createdDate(LocalDateTime.now())
                .build().toEntity()
        );
    }

    /**
     * qna 게시글을 수정한다 (update)
     * @param newQuestionDto
     * @return void
     */

    public void modifyQuestion(QuestionModifyDto newQuestionDto, MemberGetMemberIdDto memberIdDto) {
        QuestionDto questionDto = new QuestionDto(questionRepository.findById(newQuestionDto.getId()).get());
        questionDto.setTitle(newQuestionDto.getTitle());
        questionDto.setContent(newQuestionDto.getContent());
        questionRepository.save(questionDto.toEntity());
    }

    /**
     * qna 게시물 삭제
     * @param questionIdDto
     * @param memberIdDto
     * @return void
     */
    public void deleteQuestion(QuestionGetIdDto questionIdDto, MemberGetMemberIdDto memberIdDto) {
        questionRepository.deleteById(questionIdDto.getId());
    }
}
