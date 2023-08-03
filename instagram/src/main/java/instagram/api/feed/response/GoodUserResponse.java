package instagram.api.feed.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GoodUserResponse {

    List<UserDto> users = new ArrayList<>();

    @Getter @Setter
    public static class UserDto{
        private Long userId;
        private String userProfileImage;
        private String username;
        private String nickname;

        @Builder
        public UserDto(Long userId, String userProfileImage, String username, String nickname) {
            this.userId = userId;
            this.userProfileImage = userProfileImage;
            this.username = username;
            this.nickname = nickname;
        }
    }
}
