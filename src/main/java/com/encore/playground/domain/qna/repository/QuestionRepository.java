package com.encore.playground.domain.qna.repository;

import com.encore.playground.domain.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
