package instagram.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import instagram.api.user.dto.UserData;
import instagram.api.user.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static instagram.entity.feed.QFeed.feed;
import static instagram.entity.user.QFollow.follow;
import static instagram.entity.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<UserData> findFollowingUsersByUsername(String username, Pageable pageable) {
        List<UserData> userData = jpaQueryFactory.select(Projections.constructor(UserData.class,
                        user.id,
                        user.profileImgUrl,
                        user.username,
                        user.nickname
                ))
                .from(follow)
                .join(follow.toUser, user)
                .where(follow.fromUser.username.eq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                        user.count()
                )
                .from(follow)
                .join(follow.toUser, user)
                .where(follow.fromUser.username.eq(username))
                .fetchOne();

        return new PageImpl<UserData>(userData, pageable, count);
    }

    @Override
    public ProfileResponse findByUsernameProfile(String username) {
        JPQLQuery<Long> follower = JPAExpressions.select(follow.count())
                .from(follow)
                .where(follow.toUser.eq(user));
        JPQLQuery<Long> following = JPAExpressions.select(follow.count())
                .from(follow)
                .where(follow.fromUser.eq(user));
        return jpaQueryFactory.select(Projections.constructor(ProfileResponse.class,
                        user.id,
                        user.profileImgUrl,
                        user.username,
                        user.nickname,
                        user.description,
                        feed.count(),
                        follower,
                        following
                ))
                .from(user)
                .leftJoin(feed).on(feed.user.eq(user))
                .groupBy(user)
                .where(user.username.eq(username))
                .fetchOne();
    }
}
