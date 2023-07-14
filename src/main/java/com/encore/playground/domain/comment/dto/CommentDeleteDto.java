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
@Schema(description = "댓글 삭제용 DTO")
public class CommentDeleteDto {
    @NotBlank(message = "삭제할 댓글의 id를 입력해주세요.")
    @Schema(description = "삭제할 댓글의 댓글 테이블 id", example = "1")
    private long id;
}
