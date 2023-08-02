package instagram.api.user.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserData {
    private Long userId;
    private String userProfileImage;
    private String username;
    private String nickname;

    public UserData(Long userId, String userProfileImage, String username, String nickname) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.nickname = nickname;
    }
}
