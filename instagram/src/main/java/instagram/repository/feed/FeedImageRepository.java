package instagram.repository.feed;

import instagram.entity.feed.Bookmark;
import instagram.entity.feed.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
    List<FeedImage> findByFeedId(Long feedId);
  
    List<FeedImage> findAllByFeedId(Long feedId);

    @Modifying
    @Transactional
    @Query("delete FeedImage i where i.feed.id = :feedId")
    void deleteAllByFeedId(@PathVariable("feedId") Long feedId);

    Long countByFeedId(long feedId);
}
