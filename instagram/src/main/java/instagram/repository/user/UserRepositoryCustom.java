package instagram.repository.user;

import instagram.api.user.dto.UserData;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    PageImpl<UserData> findFollowingUsers(Long userId, Pageable pageable);
}
