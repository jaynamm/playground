package com.encore.playground.domain.qna.repository;

import com.encore.playground.domain.qna.entity.Answer;
import com.encore.playground.domain.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<List<Answer>> findAnswerByQuestion_Id(Long questionId);
    Optional<List<Answer>> findByMemberId(Long memberId);
}
