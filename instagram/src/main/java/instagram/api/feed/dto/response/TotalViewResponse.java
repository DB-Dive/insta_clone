package instagram.api.feed.dto.response;

import instagram.api.feed.dto.MiniFeedDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TotalViewResponse {
    private List<FeedDto> feeds = new ArrayList<>();
    private int totalPage;
    private int currentPage;

    @Builder
    public TotalViewResponse(List<FeedDto> feeds, int totalPage, int currentPage) {
        this.feeds = feeds;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "TotalViewResponse{" +
                "feeds=" + feeds +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
