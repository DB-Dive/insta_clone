package instagram.repository.feed;

import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface FeedHashTagRepository extends JpaRepository<FeedHashTag, Long> {

    @Query(value = "select fh.feed from FeedHashTag fh where fh.hashtag.tagname = :tagname")
    List<Feed> findFeedByTagname(@Param("tagname") String tagName);

    @Query(value = "select fh.feed from FeedHashTag fh where fh.hashtag.tagname = :tagname order by fh.feed.createdAt desc")
    List<Feed> findFeedByTagnameOrderByCreateAtDesc(@Param("tagname") String tagName);
}
