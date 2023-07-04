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
@Schema(description = "피드 삭제용 DTO")
public class FeedDeleteDto {
    @Schema(description = "피드 테이블 id", example = "1")
    private Long id;
}
