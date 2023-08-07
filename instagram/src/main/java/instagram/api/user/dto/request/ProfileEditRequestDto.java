package instagram.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileEditRequestDto {
    private String username;
    private String nickname;
//    private MultipartFile userProfileImage;
    private String description;

    public ProfileEditRequestDto(String username, String nickname, MultipartFile userProfileImage, String description) {
        this.username = username;
        this.nickname = nickname;
//        this.userProfileImage = userProfileImage;
        this.description = description;
    }
}
