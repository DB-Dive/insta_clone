package instagram.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import instagram.api.user.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static instagram.entity.user.QFollow.follow;
import static instagram.entity.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<UserData> findFollowingUsers(Long userId, Pageable pageable) {
        List<UserData> userData = jpaQueryFactory.select(Projections.constructor(UserData.class,
                        user.id,
                        user.profileImgUrl,
                        user.username,
                        user.nickname
                ))
                .from(follow)
                .join(follow.toUser, user)
                .where(follow.fromUser.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                        user.count()
                )
                .from(follow)
                .join(follow.toUser, user)
                .where(follow.fromUser.id.eq(userId))
                .fetchOne();

        return new PageImpl<UserData>(userData, pageable, count);
    }
}
