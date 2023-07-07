package com.encore.playground.domain.qna.dto;

import com.encore.playground.domain.qna.entity.Answer;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class AnswerListDto {
    private Long id;
    private String content;
    private Long memberId;
    private String userId;
    private String nickname;
    private LocalDateTime createdDate;
    private Long questionId;
    private boolean isEditable;

    public AnswerListDto(Answer entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.memberId = entity.getMember().getId();
        this.userId = entity.getMember().getUserid();
        this.nickname = entity.getMember().getNickname();
        this.createdDate = entity.getCreatedDate();
        this.questionId = entity.getQuestion().getId();
        this.isEditable = false;
    }
}
