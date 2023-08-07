package instagram.api.feed.dto.response;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class TotalViewResponse {
    private List<FeedDto> feeds;
    private int totalPage;
    private int currentPage;


    public TotalViewResponse(PageImpl<FeedDto> feeds) {
        this.feeds = feeds.getContent();
        this.totalPage = feeds.getTotalPages();
        this.currentPage = feeds.getNumber();
    }
}
