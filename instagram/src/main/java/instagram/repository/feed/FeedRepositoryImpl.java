package instagram.repository.feed;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import instagram.api.user.dto.FeedData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static instagram.entity.comment.QComment.comment;
import static instagram.entity.feed.QFeed.feed;
import static instagram.entity.feed.QFeedGood.feedGood;
import static instagram.entity.feed.QFeedImage.feedImage;
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
}
