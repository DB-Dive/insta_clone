package instagram.repository.feed;

import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedGoodRepository extends JpaRepository<FeedGood, Long> {
    int countByUserIdAndFeedId(Long userId, Long feedId);

    int countByFeed(Feed feed);

    FeedGood findByFeedAndUser(Feed feed, User user);

    @Query(value = "select g.user from FeedGood g where g.feed.id = :feedId")
    List<User> findUserByFeedId(@Param("feedId") Long feedId);

    Long countByFeedId(Long feedId);

    Optional<FeedGood> findByUserIdAndFeedId(Long userId, Long feedId);
}
