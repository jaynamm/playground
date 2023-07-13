package com.encore.playground.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 수정용 DTO")
public class CommentModifyDto {
    @Schema(description = "수정할 댓글의 댓글 테이블 id", example = "1")
    private long id;
    @Schema(description = "수정할 댓글의 내용", example = "수정된 댓글 내용")
    private String content;
}
