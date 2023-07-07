package com.encore.playground.domain.feed.dto;

import com.encore.playground.domain.feed.entity.Feed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "피드 출력용 DTO") // 해당 json 구조에 대한 설명
public class FeedListDto {
    @Schema(description = "피드 테이블 id", example = "1") // 각 속성에 대한 설명
    private Long id;

    @Schema(description = "피드 작성자의 테이블 id", example = "1")
    private Long memberId;

    @Schema(description = "피드 작성자의 userId", example = "피드 작성자")
    private String userId;

    @Schema(description = "피드 작성자의 닉네임", example = "닉네임")
    private String nickname;

    @Schema(description = "피드 작성일자", example = "2023-05-17 19:37:38.571587")
    private LocalDateTime createdDate;

    @Schema(description = "피드 수정일자", example = "2023-05-17 19:37:35.890895")
    private LocalDateTime modifiedDate;

    @Schema(description = "피드 좋아요 수", example = "0")
    private Integer likeCount;

    @Schema(description = "피드 댓글 수", example = "0")
    private Integer commentCount;

    @Schema(description = "피드 조회수", example = "0")
    private Integer viewCount;

    @Schema(description = "피드 내용", example = "피드 내용")
    private String content;

    public FeedListDto(Feed entity) {
        this.id = entity.getId();
        this.memberId = entity.getMember().getId();
        this.userId = entity.getMember().getUserid();
        this.nickname = entity.getMember().getNickname();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.likeCount = entity.getLikeCount();
        this.viewCount = entity.getViewCount();
        this.content = entity.getContent();
    }
}
