package instagram.repository.feed;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import instagram.api.feed.dto.response.FeedDto;
import instagram.api.user.dto.FeedData;
import instagram.entity.feed.QBookmark;
import instagram.entity.user.QFollow;
import instagram.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static instagram.entity.comment.QComment.comment;
import static instagram.entity.feed.QBookmark.bookmark;
import static instagram.entity.feed.QFeed.feed;
import static instagram.entity.feed.QFeedGood.feedGood;
import static instagram.entity.feed.QFeedImage.feedImage;
import static instagram.entity.user.QFollow.follow;
import static instagram.entity.user.QUser.user;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<FeedData> findFeedInfo(String username, Pageable pageable) {
        JPQLQuery<String> feedImageUrl = JPAExpressions.select(feedImage.feedImgUrl)
                .from(feedImage)
                .where(feedImage.id.eq(JPAExpressions.select(
                                        feedImage.id.min()
                                )
                                .from(feedImage)
                                .where(feedImage.feed.eq(feed))
                ));
        List<FeedData> feedData = jpaQueryFactory.select(Projections.constructor(FeedData.class,
                        feed.id,
                        feedImageUrl,
                        feedGood.count(),
                        comment.count()
                ))
                .from(feed)
                .leftJoin(feedGood).on(feedGood.feed.eq(feed))
                .leftJoin(comment).on(comment.feed.eq(feed))
                .join(feed.user, user)
                .where(user.username.eq(username))
                .groupBy(feed)
                .orderBy(feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                        feed.count()
                )
                .from(feed)
                .join(feed.user, user)
                .where(user.username.eq(username))
                .fetchOne();
        return new PageImpl<FeedData>(feedData, pageable, count);

    }

    @Override
    public PageImpl<FeedDto> findAllFeeds(Long userId, Pageable pageable) {
        JPQLQuery<Long> goodStatus = JPAExpressions.select(feedGood.count())
                .from(feedGood)
                .where(feedGood.user.id.eq(userId), feedGood.feed.eq(feed));
        JPQLQuery<Long> bookmarkStatus = JPAExpressions.select(bookmark.count())
                .from(bookmark)
                .where(bookmark.user.id.eq(userId), bookmark.feed.eq(feed));
        JPQLQuery<User> followingUser = JPAExpressions.select(follow.toUser)
                .from(follow)
                .join(follow.toUser, user)
                .where(follow.fromUser.id.eq(userId));

        List<FeedDto> feeds = jpaQueryFactory.select(Projections.constructor(FeedDto.class,
                        user.id,
                        user.profileImgUrl,
                        user.username,
                        feed.id,
                        feed.content,
                        feed.createdAt,
                        comment.countDistinct(),
                        feedGood.countDistinct(),
                        goodStatus,
                        bookmarkStatus
                ))
                .from(feed)
                .join(feed.user, user)
                .leftJoin(comment).on(feed.eq(comment.feed))
                .leftJoin(feedGood).on(feed.eq(feedGood.feed))
                .where(feed.user.in(followingUser).or(feed.user.id.eq(userId)))
                .orderBy(feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(feed.id)
                .fetch();

        List<Long> feedId = feeds.stream()
                .map(FeedDto::getFeedId)
                .collect(Collectors.toList());

        Map<Long, List<String>> feedImages = jpaQueryFactory.select(
                        feed.id,
                        feedImage.feedImgUrl
                )
                .from(feed)
                .leftJoin(feedImage).on(feedImage.feed.eq(feed))
                .where(feed.id.in(feedId))
                .orderBy(feedImage.id.asc())
                .transform(GroupBy.groupBy(feed.id)
                        .as(GroupBy.list(
                                feedImage.feedImgUrl
                        ))
                );
        feeds.forEach(f ->
                f.setFeedImgUrl(feedImages.getOrDefault(f.getFeedId(), new ArrayList<>()))
        );

        Long count = jpaQueryFactory.select(
                        feed.count()
                )
                .from(feed)
                .join(feed.user, user)
                .where(feed.user.in(followingUser))
                .fetchOne();

        return new PageImpl<FeedDto>(feeds, pageable, count);
    }
}
