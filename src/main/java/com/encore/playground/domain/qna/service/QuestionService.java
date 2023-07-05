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

    public List<QuestionDto> questionList() {
        List<QuestionDto> questionDtoList = questionRepository.findAll().stream().map(QuestionDto::new).toList();
        return questionDtoList;
    }

    public boolean isQuestionWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        return memberIdDto.getUserid().equals(questionRepository.findById(id).get().getMember().getUserid());
    }

    /**
     * id에 해당하는 작성자가 작성한 qna 게시물 목록을 가져온다.
     * @param memberId
     * @return List<QuestionDto>
     */

    public List<QuestionDto> getQuestionListByMember(Long memberId) {
        List<QuestionDto> questionDtoList = questionRepository.findByMemberId(memberId).get().stream().map(QuestionDto::new).toList();
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
     * @return List<QuestionDto> (qna 메인)
     */
    public List<QuestionDto> writeQuestion(QuestionWriteDto questionWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        questionRepository.save(QuestionDto.builder()
                .title(questionWriteDto.getTitle())
                .member(memberDto.toEntity())
                .content(questionWriteDto.getContent())
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

    public List<QuestionDto> modifyQuestion(QuestionModifyDto newQuestionDto, MemberGetMemberIdDto memberIdDto) {
        QuestionDto questionDto = new QuestionDto(questionRepository.findById(newQuestionDto.getId()).get());
        questionDto.setTitle(newQuestionDto.getTitle());
        questionDto.setContent(newQuestionDto.getContent());
        questionRepository.save(questionDto.toEntity());
        return questionList();
    }

    /**
     * qna 게시물 삭제
     * @param questionDeleteDto
     * @param memberIdDto
     * @return questionList();
     */
    public List<QuestionDto> deleteQuestion(QuestionGetIdDto questionIdDto, MemberGetMemberIdDto memberIdDto) {
        questionRepository.deleteById(questionIdDto.getId());
        return questionList();
    }

}