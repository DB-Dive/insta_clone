package instagram.api.feed.dto;

import lombok.Getter;

@Getter
public class FeedImageDto {
    private Long feedImageId;
    private String feedImage;

    public FeedImageDto(Long feedImageId, String feedImage) {
        this.feedImageId = feedImageId;
        this.feedImage = feedImage;
    }

    @Override
    public String toString() {
        return "FeedImageDto{" +
                "feedImageId=" + feedImageId +
                ", feedImage='" + feedImage + '\'' +
                '}';
    }
}
