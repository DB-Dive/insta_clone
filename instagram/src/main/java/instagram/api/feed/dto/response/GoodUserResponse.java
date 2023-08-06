package instagram.api.feed.dto.response;

import instagram.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

        public UserDto(User user){
            this.userId = user.getId();
            this.userProfileImage = user.getProfileImgUrl();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
        }

        @Builder
        public UserDto(Long userId, String userProfileImage, String username, String nickname) {
            this.userId = userId;
            this.userProfileImage = userProfileImage;
            this.username = username;
            this.nickname = nickname;
        }
    }

    public GoodUserResponse(List<UserDto> users) {
        this.users = users;
    }
}
