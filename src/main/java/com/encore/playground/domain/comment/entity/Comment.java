package com.encore.playground.domain.comment.entity;

import com.encore.playground.domain.feed.entity.Feed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "comment", // 테이블 이름
        uniqueConstraints = { // UNIQUE 제약조건
                @UniqueConstraint(
                        name = "no_same_number_in a_feed", // 제약조건 이름
                        columnNames = {"feed_no", "comment_no"} // 해당 2개 컬럼은 2개 조합이 해당 테이블에서 유일해야 함
                )
        },
        indexes = { // 인덱스 설정
                @Index(name = "feedno_comment_idx", columnList = "feed_no"),
                @Index(name = "userid_comment_idx", columnList = "userid")
        })
@Getter
@Builder
@Entity
public class Comment {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentid; // 댓글 테이블 고유 번호
    @ManyToOne
    @JoinColumn(name = "feed_no", nullable = false) // 외래키 이름
    private Feed feed; // 피드 번호
    @Column(nullable = false)
    private String userid; // 작성자
    @Column(nullable = false, name = "comment_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentNo; // 해당 피드에서의 댓글 번호
    @Column(nullable = false)
    private LocalDateTime uploadTime; // 댓글 작성시각
    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount; // 좋아요 수
    @Column(nullable = false, length = 1000)
    private String article; // 댓글 내용
}
