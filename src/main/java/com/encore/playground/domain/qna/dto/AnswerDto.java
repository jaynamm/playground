package com.encore.playground.domain.qna.dto;

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
    private String memberId;
    private LocalDateTime createdDate;
    private Question question;

    // Entity -> DTO
    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.memberId = answer.getMemberId();
        this.createdDate = answer.getCreatedDate();
        this.question = answer.getQuestion();
    }

    public Answer toEntity() {
        return Answer.builder()
                .id(id)
                .content(content)
                .memberId(memberId)
                .createdDate(createdDate)
                .question(question)
                .build();
    }
}
