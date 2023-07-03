package com.encore.playground.domain.qna.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWriteDto {
    private String title;
    private String content;
}
