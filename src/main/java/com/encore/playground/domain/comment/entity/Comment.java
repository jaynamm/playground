package com.encore.playground.domain.comment.entity;

import com.encore.playground.domain.feed.entity.Feed;
import com.encore.playground.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 테이블 고유 번호

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feed_id", nullable = false) // 외래키 이름
    private Feed feed; // 피드 번호

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false) // 외래키 이름
    private Member member; // 작성자의 멤버 id

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate; // 댓글 작성시각

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 댓글 수정시각

    @Column(nullable = false, length = 1000)
    private String content; // 댓글 내용
}
