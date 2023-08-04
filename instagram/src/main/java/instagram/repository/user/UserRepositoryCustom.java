package instagram.repository.user;

import instagram.api.user.dto.UserData;
import instagram.api.user.dto.response.ProfileResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    PageImpl<UserData> findFollowingUsersByUsername(String username, Pageable pageable);
    ProfileResponse findByUsernameProfile(String username);
}
