package com.encore.playground.domain.qna.service;

import com.encore.playground.domain.member.dto.MemberDto;
import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.member.service.MemberService;
import com.encore.playground.domain.qna.dto.*;
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
    private final MemberService memberService;

    public AnswerListDto isAnswerWriter (AnswerDto answerDto, MemberGetMemberIdDto memberIdDto) {
        AnswerListDto answerListDto = new AnswerListDto(answerDto.toEntity());
        answerListDto.setEditable(answerListDto.getUserId().equals(memberIdDto.getUserid()));
        return answerListDto;
    }

    public boolean isAnswerWriter(Long id, MemberGetMemberIdDto memberIdDto) {
        return memberIdDto.getUserid().equals(answerRepository.findById(id).get().getMember().getUserid());
    }

    public List<AnswerListDto> getAnswerList(Long questionId, MemberGetMemberIdDto memberIdDto) {
        List<AnswerDto> answerDtoList = answerList(questionId);
        return answerDtoList.stream().map(AnswerDto -> isAnswerWriter(AnswerDto, memberIdDto)).toList();
    }

    public List<AnswerDto> answerList(Long questionId) {
        return answerRepository.findAnswerByQuestion_Id(questionId).get().stream().map(AnswerDto::new).toList();
    }

    public List<AnswerDto> getAnswerListByMember(MemberDto memberDto) {
        return answerRepository.findByMemberId(memberDto.getId()).get().stream().map(AnswerDto::new).toList();
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
        answerRepository.delete(answerRepository.findById(answerDeleteDto.getId()).get());
    }

}
