package instagram.api.feed.dto.response;

import instagram.entity.feed.FeedImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class FeedDto {
    private Long userId;
    private String userProfileImage;
    private String username;

    private Long feedId;
    private String content;
    private LocalDateTime createdAt;

    private Long commentCnt;
    private Long goodCnt;

    private boolean goodStatus;
    private boolean bookmarkStatus;

    private List<String> feedImgUrl;

    public FeedDto(Long userId, String userProfileImage, String username, Long feedId, String content, LocalDateTime createdAt, Long commentCnt, Long goodCnt, Long goodStatus, Long bookmarkStatus) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.feedId = feedId;
        this.content = content;
        this.createdAt = createdAt;
        this.commentCnt = commentCnt;
        this.goodCnt = goodCnt;
        this.goodStatus = goodStatus != 0L;
        this.bookmarkStatus = bookmarkStatus != 0L;
    }
}
