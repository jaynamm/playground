package com.encore.playground.domain.follow.entity;

import com.encore.playground.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_member", nullable = false)
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member", nullable = false)
    private Member toMember;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate; //팔로우 시각

}
