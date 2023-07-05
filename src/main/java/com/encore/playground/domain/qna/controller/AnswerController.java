package com.encore.playground.domain.qna.controller;

import com.encore.playground.domain.member.dto.MemberGetMemberIdDto;
import com.encore.playground.domain.qna.dto.*;
import com.encore.playground.domain.qna.service.AnswerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/qna")
@RestController
public class AnswerController {
    private final AnswerService answerService;

    /**
     * POST - id에 해당하는 질문의 답변 목록 조회
     * @param getQuestionId - question_id
     * @return  List<AnswerDTO>
     */
    @PostMapping("/answer/list")
    private List<AnswerDto> answerList(@RequestBody Map<String, Long> getQuestionId) {
        Long questionId = getQuestionId.get("question_id");
        return answerService.answerList(questionId);
    }

    /**
     * GET - memberId에 해당하는 답변 목록 조회
     * @param memberId
     * @return List<AnswerDTO>
     */
    @GetMapping("/answer/list/{memberId}")
    private List<AnswerDto> getAnswerListByMember(@PathVariable Long memberId) {
        return answerService.getAnswerListByMember(memberId);
    }

    /**
     * POST - 질문 답변 생성
     * @param answerWriteDto
     * @param request
     * @return List<AnswerDTO>
     */
    @PostMapping("/answer/write")
    private List<AnswerDto> writeAnswer(@RequestBody AnswerWriteDto answerWriteDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");

        return answerService.createAnswer(answerWriteDto, memberIdDto);
    }


    @GetMapping("/answer/modify")
    private String modifyAnswerButton(@RequestBody AnswerGetIdDto answerIdDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (answerService.isAnswerWriter(answerIdDto.getId(), memberIdDto)) {
            return "forward:/answer/modify";
        } else {
            return "redirect:/answer/list";
        }
    }

    /**
     * Post - 질문에 대한 답변을 수정
     * @param answerModifyDto
     * @param request
     * @return List<AnswerDto>
     */
    @PostMapping("/answer/modify")
    private List<AnswerDto> modifyAnswer(@RequestBody AnswerModifyDto answerModifyDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (answerService.isAnswerWriter(answerModifyDto.getId(), memberIdDto)) {
            return answerService.modifyAnswer(answerModifyDto, memberIdDto);
        } else {
            return answerService.answerList(answerModifyDto.getQuestionId());
        }

    }

    /**
     * Post - 질문에 대한 답변 삭제
     * @param answerDeleteDto
     * @param request
     * @return List<AnswerDto>
     */

    @PostMapping("/answer/delete")
    private List<AnswerDto> deleteAnswer(@RequestBody AnswerDeleteDto answerDeleteDto, HttpServletRequest request) {
        MemberGetMemberIdDto memberIdDto = (MemberGetMemberIdDto) request.getAttribute("memberIdDto");
        if (answerService.isAnswerWriter(answerDeleteDto.getId(), memberIdDto)) {
            return answerService.deleteAnswer(answerDeleteDto, memberIdDto);
        } else {
            return answerService.answerList(answerDeleteDto.getQuestionId());
        }
    }
}
