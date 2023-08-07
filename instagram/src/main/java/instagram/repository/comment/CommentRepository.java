package instagram.repository.comment;

import instagram.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByFeedId(Long feedId, Pageable pageable);

    List<Comment> findAllByFeedId(Long feedId);

    int countByFeedId(Long feedId);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.feed.id = :feedId")
    void deleteAllByFeedId(@PathVariable("feedId") Long feedId);
}
