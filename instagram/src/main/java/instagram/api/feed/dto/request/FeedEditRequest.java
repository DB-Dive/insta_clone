package instagram.api.feed.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedEditRequest {
    private List<String> tags;
    private String content;

    @Builder
    public FeedEditRequest(List<String> tags, String content) {
        this.tags = tags;
        this.content = content;
    }
}
