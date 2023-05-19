package com.encore.playground.domain.qna.entity;

import com.encore.playground.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // TODO: author 를 member_id 로 참조해서 가져와야한다.
    @Column(nullable = false)
    private String memberId;

    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    private Question question;
}
