package instagram.repository.feed;

import instagram.api.feed.dto.TestDto;
import instagram.api.feed.dto.response.HashTagResponse;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.feed.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    @Query(value = "select t.feedId from HashTag t where t.tagname = :tagname")
    List<Long> findAllFeedIdByTagname(@Param("tagname") String tagName);

    @Query(value = "select f from HashTag h left join Feed f on h.feedId = f.id where h.tagname = :tagname order by f.createdAt desc")
    List<Feed> find(@Param("tagname") String tagName);

    @Query(value =
            "select new instagram.api.feed.dto.TestDto" +
                    "(f.id, (select i.feedImgUrl from FeedImage i where i.id in ("+"" +
                    "select min(ii.id) from FeedImage ii where ii.feed = f)),"+
                    " count(fg), count(c))" +
                    " from Feed f left join HashTag h on f.id = h.feedId" +
                    " left join FeedGood fg on fg.feed = f" +
                    " left join Comment c on c.feed = f" +
                    " where h.tagname = :tagname" +
                    " group by f.id" +
                    " order by f.createdAt desc"
    )
    List<TestDto> dopeQuery(@Param("tagname") String tagName);

    @Query(value = "select f from FeedImage i join i.feed f group by i.feed")
    List<Feed> testQuery();

    @Modifying
    @Transactional
    @Query(value = "delete from HashTag h where h.feedId = :feedId")
    void deleteAllByFeedId(@Param("feedId") Long feedId);

    @Query(value = "select h from HashTag h where h.feedId = :feedId")
    List<HashTag> findAllByFeedId(@Param("feedId") Long feedId);
}