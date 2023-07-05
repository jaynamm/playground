package com.encore.playground.domain.qna.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionModifyDto {
    private Long id;
    private String title;
    private String content;
}
