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
    private String author;
    private LocalDateTime createdDate;
    private Long questionId;

    // Entity -> DTO
    public AnswerDto (Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.author = answer.getAuthor();
        this.createdDate = answer.getCreatedDate();
        this.questionId = answer.getQuestionId();
    }

    public Answer toEntity() {
        return Answer.builder()
                .id(id)
                .content(content)
                .author(author)
                .createdDate(createdDate)
                .questionId(questionId)
                .build();
    }
}