package instagram.repository.feed;

import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
//    Optional<Bookmark> findByFeedAndUser(Feed feed, User user);

    Optional<Bookmark> findByFeedIdAndUserId(Long feedId, Long userId);

    List<Bookmark> findAllByUserId(Long userId);

    @Query("SELECT f FROM Bookmark b JOIN b.feed f JOIN b.user u ON u.id = :userId ORDER BY b.id")
    Page<Feed> findFeedByUserId(@Param("userId") Long userId, Pageable pageable);

    Optional<Bookmark> findByUserIdAndFeedId(Long userId, Long feedId);
}