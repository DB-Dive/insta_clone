package instagram.api.feed.dto;

import instagram.entity.feed.FeedImage;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SelectViewResponse {
    private Long userId;
    private String userProfileImage;
    private String username;

    private Long feedId;
    private String content;
    private List<FeedImageDto> feedImages = new ArrayList<>();

    private CommentsDto comments;

    private Long goodCnt;
    private boolean goodStatus;

    private boolean bookmarkStatus;

    @Builder
    public SelectViewResponse(Long userId, String userProfileImage, String username, Long feedId, String content, List<FeedImageDto> feedImages, CommentsDto comments, Long goodCnt, boolean goodStatus, boolean bookmarkStatus) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.feedId = feedId;
        this.content = content;
        this.feedImages = feedImages;
        this.comments = comments;
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus;
        this.bookmarkStatus = bookmarkStatus;
    }

    @Override
    public String toString() {
        return "SelectViewResponse{" +
                "userId=" + userId +
                ", userProfileImage='" + userProfileImage + '\'' +
                ", username='" + username + '\'' +
                ", feedId=" + feedId +
                ", content='" + content + '\'' +
                ", feedImages=" + feedImages +
                ", comments=" + comments +
                ", goodCnt=" + goodCnt +
                ", goodStatus=" + goodStatus +
                ", bookmarkStatus=" + bookmarkStatus +
                '}';
    }
}
