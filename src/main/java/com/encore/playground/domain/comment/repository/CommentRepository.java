package com.encore.playground.domain.comment.repository;

import com.encore.playground.domain.comment.entity.Comment;
import com.encore.playground.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 피드 글번호를 이용하여 해당 글에 달린 댓글들 가져오기<br>
     * findBy + 외래키가 있는 테이블 이름 + "_" + 외래키 컬럼명
     * @param feedNo 외래키
     * @return 해당 글에 달린 댓글들 (Optional)
     */
    Optional<List<Comment>> findByFeed_Id (Long feedNo);

    /**
     * 유저 id를 사용하여 해당 유저가 작성한 댓글들 가져오기
     * @param userid 유저 id
     * @return 해당 유저가 작성한 댓글 (Optional) 목록
     */
    Optional<List<Comment>> findByMemberId(String userid);
}
