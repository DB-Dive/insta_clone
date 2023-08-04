package instagram.repository.feed;

import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
