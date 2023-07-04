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
@Schema(description = "피드 작성용 DTO")
public class FeedWriteDto {
    @Schema(description = "피드 글 내용", example = "피드 예제입니다")
    private String content;
}
