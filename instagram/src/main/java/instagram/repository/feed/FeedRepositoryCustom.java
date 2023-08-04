package instagram.repository.feed;

import instagram.api.user.dto.FeedData;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface FeedRepositoryCustom {
    PageImpl<FeedData>  findFeedInfo(String username, Pageable pageable);
}
