package instagram.api.user.dto.response;

import instagram.api.user.dto.FeedData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileResponse {
    private Long userId;
    private String userProfileImage;
    private String username;
    private String nickname;
    private String description;
    private Long feedCnt;
    private Long follower;
    private Long following;
    private List<FeedData> feeds;
    private int totalPage;
    private int currentPage;

    public ProfileResponse(Long userId, String userProfileImage, String username, String nickname, String description, Long feedCnt, Long follower, Long following) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.nickname = nickname;
        this.description = description;
        this.feedCnt = feedCnt;
        this.follower = follower;
        this.following = following;
    }
}
