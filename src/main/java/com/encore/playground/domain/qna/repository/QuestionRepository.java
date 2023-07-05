package com.encore.playground.domain.qna.repository;

import com.encore.playground.domain.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<List<Question>> findByMemberId(Long memberId);
}
