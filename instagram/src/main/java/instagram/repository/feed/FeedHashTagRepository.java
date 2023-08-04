package instagram.repository.feed;

import instagram.entity.feed.FeedHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedHashTagRepository extends JpaRepository<FeedHashTag, Long> {
}
