package instagram.api.user.dto;

import lombok.Getter;

@Getter
public class FeedData {
    private Long feedId;
    private String feedImage;
    private Long goodCnt;
    private Long commentCnt;

    public FeedData(Long feedId, String feedImage, Long goodCnt, Long commentCnt) {
        this.feedId = feedId;
        this.feedImage = feedImage;
        this.goodCnt = goodCnt;
        this.commentCnt = commentCnt;
    }
}
