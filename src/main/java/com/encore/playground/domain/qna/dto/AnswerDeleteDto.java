package com.encore.playground.domain.qna.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDeleteDto {
    private Long id;
    private Long questionId;
}
