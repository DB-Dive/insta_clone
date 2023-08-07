package instagram.repository.feed;

import instagram.api.feed.dto.MiniFeedDto;
import instagram.api.feed.dto.StatusDto;
import instagram.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
//    @Query("select new instagram.api.feed.dto.MiniFeedDto(u.id, u.profileImgUrl, u.username, " +
//            "fd.id, fd.content, fd.createdAt, " +
//            "min(fi), count(distinct cmt.id), count(distinct fg.id)) " +
//            "from Feed fd " +
//            "left join fd.user u " +
//            "left join FeedImage fi on fd.id = fi.feed.id " +
//            "left join Comment cmt on fd.id = cmt.feed.id " +
//            "left join FeedGood fg on fd.id = fg.feed.id " +
//            "where fd.user.id in (select fw.toUser.id from Follow fw where fw.fromUser.id = :userId) " +
//            "group by fd.id " +
//            "order by fd.createdAt")
//    List<MiniFeedDto> findFollowFeedsOneImg(@Param("userid") long userId);

//    @Query("select new instagram.api.feed.dto.StatusDto(fg.id, fd3.id) " +
//            "from Feed fd3 " +
//            "left join FeedGood fg on fd3.id = fg.feed.id " +
//            "where fg.user.id = :userId " +
//            "and fd3.id in (select fd2.id " +
//            "                     from User u2 " +
//            "                     join Feed fd2 on u2.id = fd2.user.id " +
//            "                     where u2.id in (select fw.toUser.id " +
//            "                                              from Follow fw " +
//            "                                              left join User u on fw.toUser.id = u.id " +
//            "                                              where fw.fromUser.id = :userId))")
//    List<StatusDto> goodStatus(@Param("userid") long userId);
//    @Query("select new instagram.api.feed.dto.StatusDto(bm.id, fd3.id)" +
//            "from Feed fd3 " +
//            "left join Bookmark bm on fd3.id = bm.feed.id " +
//            "where bm.user.id = :userId " +
//            "and fd3.id in (select fd2.id " +
//            "                     from User u2 " +
//            "                     join Feed fd2 on u2.id = fd2.user.id " +
//            "                     where u2.id in (select fw.toUser.id " +
//            "                                              from Follow fw " +
//            "                                              left join User u on fw.toUser.id = u.id " +
//            "                                              where fw.fromUser.id = :userId))")
//    List<StatusDto> bookmarkStatus(@Param("userid") long userId);
}
