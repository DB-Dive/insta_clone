package instagram.repository.comment;

import instagram.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByFeedId(Long feedId);

    int countByFeedId(Long feedId);
}
