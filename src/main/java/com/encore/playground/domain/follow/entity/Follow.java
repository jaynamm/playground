package com.encore.playground.domain.follow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "follow")
@Getter
@Builder
@Entity

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_user_id")
    @Column(nullable = false)
    private String fromMemberId;

    @ManyToOne
    @JoinColumn(name = "member_user_id")
    @Column(nullable = false)
    private String toMemberId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime followDate; //팔로우 시각

}
