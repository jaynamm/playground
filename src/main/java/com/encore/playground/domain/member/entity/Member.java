package com.encore.playground.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column(unique = true)  // unique 제약 조건 추가
    private String userid;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String curriculum; // 리스트 선택으로 선택할 수 있게

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;


}