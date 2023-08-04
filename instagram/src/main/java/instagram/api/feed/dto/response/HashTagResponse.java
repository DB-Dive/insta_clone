package instagram.api.feed.dto.response;

import instagram.api.feed.dto.TestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HashTagResponse {
    private String tagname;
    private int feedCount;
    private List<TestDto> feed;

    @Builder
    public HashTagResponse(String tagname, int feedCount, List<TestDto> feed) {
        this.tagname = tagname;
        this.feedCount = feedCount;
        this.feed = feed;
    }
}
