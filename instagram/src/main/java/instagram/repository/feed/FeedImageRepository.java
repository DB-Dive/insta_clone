package instagram.repository.feed;

import instagram.entity.feed.Bookmark;
import instagram.entity.feed.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
    List<FeedImage> findByFeedId(Long feedId);
  
    List<FeedImage> findAllByFeedId(Long feedId);
}
