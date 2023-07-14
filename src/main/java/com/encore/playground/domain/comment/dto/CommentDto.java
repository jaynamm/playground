package com.encore.playground.domain.comment.dto;

import com.encore.playground.domain.comment.entity.Comment;
import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private long id;
    private Feed feed;
    private Member member;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .id(id) // comment 테이블의 id (수정, 삭제용)
                .feed(feed) // comment 테이블의 feed_id (댓글 작성, 조회용)
                .member(member) // 작성자
                .createdDate(createdDate) // 작성일자
                .modifiedDate(modifiedDate) // 수정일자
                .content(content) // 댓글 내용
                .build();
    }

    public CommentDto(Comment entity) {
        this.id = entity.getId();
        this.feed = entity.getFeed();
        this.member = entity.getMember();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.content = entity.getContent();
    }
}
