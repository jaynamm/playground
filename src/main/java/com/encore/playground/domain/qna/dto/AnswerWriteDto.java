package com.encore.playground.domain.qna.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class AnswerWriteDto {
    private String content;
    private Long questionId;
}
