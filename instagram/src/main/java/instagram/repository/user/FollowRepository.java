package instagram.repository.user;

import instagram.api.user.dto.UserData;
import instagram.entity.user.Follow;
import instagram.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByToUserAndFromUser(User toUser, User fromUser);
    PageImpl<UserData> findByFromUser(Long userId, Pageable pageable);
    Page<User> findByToUser(Long userId, Pageable pageable);
}
