package com.encore.playground.domain.qna.dto;

import com.encore.playground.domain.member.entity.Member;
import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.entity.Question;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class AnswerDto {
    private Long id;
    private String content;
    private Member member;
    private LocalDateTime createdDate;
    private Question question;

    // Entity -> DTO
    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.member = answer.getMember();
        this.createdDate = answer.getCreatedDate();
        this.question = answer.getQuestion();
    }

    // DTO -> Entity
    public Answer toEntity() {
        return Answer.builder()
                .id(id)
                .content(content)
                .member(member)
                .createdDate(createdDate)
                .question(question)
                .build();
    }
}
