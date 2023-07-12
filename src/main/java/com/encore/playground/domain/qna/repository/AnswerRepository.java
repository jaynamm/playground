package com.encore.playground.domain.qna.repository;

import com.encore.playground.domain.qna.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAnswerByQuestion_IdOrderByIdDesc(Long questionId, Pageable pageable);
    Page<Answer> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);
}
