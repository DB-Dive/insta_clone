package instagram.api.user.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
    private Long id;
    private String userProfileImage;
    private String username;
    private String nickname;

}
