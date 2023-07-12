package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
    private final MemberService memberService;

    /**
     * 해당 답변의 작성자가 현재 로그인한 사용자인지 확인한다. (한 페이지에서 반복용)
     * @param answerDto 해당 답변의 DTO
     * @param memberIdDto 현재 로그인한 사용자의 DTO
     * @return AnswerListDto
     */
    public AnswerListDto isAnswerWriter (AnswerDto answerDto, MemberGetMemberIdDto memberIdDto) {
        AnswerListDto answerListDto = new AnswerListDto(answerDto.toEntity());
        answerListDto.setEditable(answerListDto.getUserId().equals(memberIdDto.getUserid()));
        return answerListDto;
    }

    /**
     * 해당 답변의 작성자가 현재 로그인한 사용자인지 확인한다. (일회용)
     * @param id 해당 답변의 id
     * @param memberIdDto 현재 로그인한 사용자의 DTO
     * @return true/false
     */
    public boolean isAnswerWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        return memberIdDto.getUserid().equals(answerRepository.findById(id).get().getMember().getUserid());
    }

    /**
     * 해당 질문의 답변 목록을 1개 페이지 가져온다.
     * @param questionId 상세보기할 질문의 id
     * @param memberIdDto 현재 로그인한 사용자의 DTO
     * @param pageable 페이지 정보
     * @return Page<AnswerListDto>
     */
    public Page<AnswerListDto> getAnswerList(Long questionId, MemberGetMemberIdDto memberIdDto, Pageable pageable) {
        Page<AnswerDto> answerDtoList = answerList(questionId,pageable);
        return answerDtoList.map(AnswerDto -> isAnswerWriter(AnswerDto, memberIdDto));
    }

    public Page<AnswerDto> answerList(Long questionId, Pageable pageable) {
        Page<Answer> answers = answerRepository.findAnswerByQuestion_IdOrderByIdDesc(questionId, pageable);
        return answers.map(AnswerDto::new);
    }

    public Page<AnswerDto> getAnswerListByMember(MemberDto memberDto, Pageable pageable) {
        return answerRepository.findByMemberIdOrderByIdDesc(memberDto.getId(), pageable).map(AnswerDto::new);
    }

    public void createAnswer(AnswerWriteDto answerWriteDto, MemberGetMemberIdDto memberIdDto) {
        MemberDto memberDto = memberService.getMemberByUserid(memberIdDto.getUserid());
        QuestionDto questionDto = questionService.readQuestion(answerWriteDto.getQuestionId(), memberIdDto);
        answerRepository.save(AnswerDto.builder()
                .content(answerWriteDto.getContent())
                .member(memberDto.toEntity())
                .createdDate(LocalDateTime.now())
                .question(questionDto.toEntity())
                .build().toEntity());
    }

    public void modifyAnswer(AnswerModifyDto newAnswerDto, MemberGetMemberIdDto memberIdDto) {
        AnswerDto answerDto = new AnswerDto(answerRepository.findById(newAnswerDto.getId()).get());
        answerDto.setContent(newAnswerDto.getContent());
        answerRepository.save(answerDto.toEntity());
    }

    public void deleteAnswer(AnswerDeleteDto answerDeleteDto, MemberGetMemberIdDto memberIdDto) {
        AnswerDto answerDto = new AnswerDto(answerRepository.findById(answerDeleteDto.getId()).get());
        answerRepository.delete(answerDto.toEntity());
    }

}
