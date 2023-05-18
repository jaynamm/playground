package com.encore.playground.domain.qna.entity;

import com.encore.playground.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // TODO: author 를 member_id 로 참조해서 가져와야한다.
    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;
}
