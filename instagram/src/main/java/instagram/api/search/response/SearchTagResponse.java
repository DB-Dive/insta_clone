package instagram.api.search.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchTagResponse {

    private String tagname;
    private Long feedCnt;

    public SearchTagResponse(String tagname, Long feedCnt) {
        this.tagname = tagname;
        this.feedCnt = feedCnt;
    }
}
