package com.encore.playground.domain.feed.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 피드 글 구조를 위한 클래스 (엔티티)
 */
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 만들어줌
@Table(name = "feed") // DB에 들어갈 테이블 이름
@Getter // 모든 필드의 getter 메소드를 자동으로 생성
@Builder // 빌더 패턴 클래스 생성
@Entity // JPA 엔티티임을 나타냄
public class Feed {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 글번호
    @Column(nullable = false, length = 20)
    private String memberId; // 작성자
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate; // 작성일자
    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정일자
    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount; // 좋아요 수
    @Column(nullable = false)
    @ColumnDefault("0")
    private int commentCount; // 댓글 수
    @Column(nullable = false)
    @ColumnDefault("0")
    private int commentTotalCount; // 댓글이 저장된 횟수. 댓글 번호를 지정하기 위하여 사용
    @Column(nullable = false)
    @ColumnDefault("0")
    private int viewCount; // 조회 수
    @Column(nullable = false, length = 1000)
    private String content; // 글 내용
}