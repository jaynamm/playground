package com.encore.playground.domain.qna.repository;

import com.encore.playground.domain.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswerByQuestion_id(Long questionId);
}
