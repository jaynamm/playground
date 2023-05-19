package com.encore.playground.domain.qna.dto;

import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.entity.Question;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private String memberId;
    private String content;
    private LocalDateTime createdDate;

    /**
     * entity -> dto
     * @param question
     */
    public QuestionDto (Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.memberId = question.getMemberId();
        this.content = question.getContent();
        this.createdDate = question.getCreatedDate();
    }

    /**
     * dto -> entity
     * @return question
     */
    public Question toEntity() {
        return Question.builder()
                .title(title)
                .memberId(memberId)
                .content(content)
                .createdDate(createdDate)
                .build();
    }

}