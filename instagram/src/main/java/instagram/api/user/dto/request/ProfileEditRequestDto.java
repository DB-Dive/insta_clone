package instagram.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEditRequestDto {
    private String username;
    private String nickname;
    private String userProfileImage;
    private String description;

    public ProfileEditRequestDto(String username, String nickname, String userProfileImage, String description) {
        this.username = username;
        this.nickname = nickname;
        this.userProfileImage = userProfileImage;
        this.description = description;
    }
}
