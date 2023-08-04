package instagram.api.feed.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HashTagResponse {
    private String tagname;
    private int feedCount;
    private List<Res> feed = new ArrayList<>();

    @Getter
    public static class Res {
        private Long id;
        private String feedImage;
        private int goodCnt;
        private int commentCnt;

        @Builder
        public Res(Long id, String feedImage, int goodCnt, int commentCnt) {
            this.id = id;
            this.feedImage = feedImage;
            this.goodCnt = goodCnt;
            this.commentCnt = commentCnt;
        }
    }

    @Builder
    public HashTagResponse(String tagname, int feedCount, List<Res> feed) {
        this.tagname = tagname;
        this.feedCount = feedCount;
        this.feed = feed;
    }
}
