package com.encore.playground.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "피드 상세화면, 마이페이지에서 댓글을 조회하는 DTO")
public class CommentReadDto {
    @Schema(description = "댓글이 달린 피드의 feedId", example = "1")
    private Long feedId;
}
