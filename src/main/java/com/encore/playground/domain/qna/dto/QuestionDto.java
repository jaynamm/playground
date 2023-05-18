package com.encore.playground.domain.qna.dto;

import com.encore.playground.domain.qna.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdDate;


    /**
     * entity -> dto
     * @param question
     */
    public QuestionDto (Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.author = question.getAuthor();
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
                .author(author)
                .content(content)
                .createdDate(createdDate)
                .build();
    }

}