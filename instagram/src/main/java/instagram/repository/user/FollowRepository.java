package instagram.repository.user;

import instagram.entity.user.Follow;
import instagram.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByToUserAndFromUser(User toUser, User fromUser);

}
