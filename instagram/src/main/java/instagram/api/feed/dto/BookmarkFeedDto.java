package instagram.api.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookmarkFeedDto {
    private Long feedId;
    private String feedImage;
    private Long goodCnt;
    private Long commentCnt;
}
