package instagram.api.feed.dto.response;

import instagram.entity.feed.FeedImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedDto {
    private Long userId;
    private String userProfileImage;
    private String username;

    private Long feedId;
    private String content;
    private LocalDateTime createdAt;

    private FeedImage feedImage;
    private Long commentCnt;
    private Long goodCnt;

    private boolean goodStatus;
    private boolean bookmarkStatus;

    @Builder
    public FeedDto(Long userId, String userProfileImage, String username, Long feedId, String content, LocalDateTime createdAt, FeedImage feedImage, Long commentCnt, Long goodCnt, boolean goodStatus, boolean bookmarkStatus) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.feedId = feedId;
        this.content = content;
        this.createdAt = createdAt;
        this.feedImage = feedImage;
        this.commentCnt = commentCnt;
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus;
        this.bookmarkStatus = bookmarkStatus;
    }

    @Override
    public String toString() {
        return "FeedDto{" +
                "userId=" + userId +
                ", userProfileImage='" + userProfileImage + '\'' +
                ", username='" + username + '\'' +
                ", feedId=" + feedId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", feedImage=" + feedImage +
                ", commentCnt=" + commentCnt +
                ", goodCnt=" + goodCnt +
                ", goodStatus=" + goodStatus +
                ", bookmarkStatus=" + bookmarkStatus +
                '}';
    }
}
