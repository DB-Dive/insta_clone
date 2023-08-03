package instagram.api.feed.request;

import instagram.entity.feed.FeedGood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FeedGoodRequest {

    private Long feedId;
    private Long userId;

}
