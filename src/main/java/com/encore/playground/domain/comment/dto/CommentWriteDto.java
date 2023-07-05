package com.encore.playground.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 작성용 DTO")
public class CommentWriteDto {
    @NotBlank(message = "댓글을 작성할 피드의 feedId를 입력해 주십시오.")
    @Schema(description = "댓글을 작성할 피드의 feedId", example = "1")
    private Long feedId;

    @NotBlank(message = "댓글의 내용을 입력해 주십시오.")
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;
}
