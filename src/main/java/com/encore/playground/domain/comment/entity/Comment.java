package com.encore.playground.domain.comment.entity;

import com.encore.playground.domain.feed.entity.Feed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
//        , // 테이블 이름
//        indexes = { // 인덱스 설정
//                @Index(name = "feedid_comment_idx", columnList = "feed_id"),
//                @Index(name = "member_id_comment_idx", columnList = "member_id")
//        })
@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 댓글 테이블 고유 번호
    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false) // 외래키 이름
    private Feed feed; // 피드 번호
    @Column(nullable = false)
    private String memberId; // 작성자
//    @Column(nullable = false, name = "comment_no")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int commentNo; // 해당 피드에서의 댓글 번호
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate; // 댓글 작성시각
    @LastModifiedDate
    private LocalDateTime modifiedDate; // 댓글 수정시각
    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount; // 좋아요 수
    @Column(nullable = false, length = 1000)
    private String content; // 댓글 내용
}
