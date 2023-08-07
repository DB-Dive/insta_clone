package instagram.api.feed.dto;

import instagram.entity.feed.FeedImage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MiniFeedDto {

    private Long userId;
    private String userProfileImage;
    private String username;

    private Long feedId;
    private String content;
    private LocalDateTime createdAt;

    private FeedImage feedImage;
    private Long commentCnt;
    private Long goodCnt;

    public MiniFeedDto(Long userId, String userProfileImage, String username, Long feedId, String content, LocalDateTime createdAt, FeedImage feedImage, Long commentCnt, Long goodCnt) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.feedId = feedId;
        this.content = content;
        this.createdAt = createdAt;
        this.feedImage = feedImage;
        this.commentCnt = commentCnt;
        this.goodCnt = goodCnt;
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
                '}';
    }
}
