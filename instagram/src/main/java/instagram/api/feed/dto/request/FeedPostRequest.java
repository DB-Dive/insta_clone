package instagram.api.feed.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedPostRequest {

    private String content;
    private String tag;

    @Builder

    public FeedPostRequest(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }
}
