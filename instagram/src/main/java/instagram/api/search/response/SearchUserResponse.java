package instagram.api.search.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchUserResponse {

    private Long userId;
    private String userProfileImage;
    private String username;
    private String nickname;

    public SearchUserResponse(Long id, String userProfileImage, String username, String nickname) {
        this.userId = id;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.nickname = nickname;
    }
}
