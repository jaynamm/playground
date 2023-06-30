package com.encore.playground.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(description = "피드 수정용 DTO")
public class FeedModifyDto {
    @Schema(description = "피드 테이블 id", example = "1")
    private Long id;

    @Schema(description = "피드 글 내용", example = "수정한 본문 예제입니다")
    private String content;
}
