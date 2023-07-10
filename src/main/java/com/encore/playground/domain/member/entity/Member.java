package com.encore.playground.domain.member.entity;

import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.entity.Question;
import com.encore.playground.global.security.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;
    @Column(unique = true, nullable = false)  // unique 제약 조건 추가, 유저 아이디
    private String userid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String curriculum;

    @Column
    private String skills; // 리스트 선택으로 선택할 수 있게

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    //member의 권한 컬럼 추가 -> 기본값으로 USER
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;
}