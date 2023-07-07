package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 목록 조회용 DTO")
public class CommentListDto {
    @Schema(description = "댓글 테이블 id", example = "1")
    private Long id;

    @Schema(description = "이 댓글이 달린 피드의 feedId", example = "1")
    private Long feedId;

    @Schema(description = "댓글 작성자의 memberId", example = "1")
    private Long memberId;

    @Schema(description = "댓글 작성자의 userId", example = "댓글 작성자")
    private String userId;

    @Schema(description = "댓글 작성자의 nickname", example = "닉네임")
    private String nickname;

    @Schema(description = "댓글 작성일자", example = "2023-05-17 19:37:38.571587")
    private LocalDateTime createdDate;

    @Schema(description = "댓글 수정일자", example = "2023-05-17 19:37:35.890895")
    private LocalDateTime modifiedDate;

    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    @Schema(description = "댓글에 수정/삭제 버튼 보여줄지 여부", example = "true")
    private boolean isEditable;

    public CommentListDto(Comment entity) {
        this.id = entity.getId();
        this.feedId = entity.getFeed().getId();
        this.memberId = entity.getMember().getId();
        this.userId = entity.getMember().getUserid();
        this.nickname = entity.getMember().getNickname();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.content = entity.getContent();
        this.isEditable = false;
    }
}
