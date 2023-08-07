package instagram.repository.feed;

import instagram.api.feed.dto.response.FeedDto;
import instagram.api.user.dto.FeedData;
import instagram.entity.user.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedRepositoryCustom {
    PageImpl<FeedData>  findFeedInfo(String username, Pageable pageable);

    PageImpl<FeedDto> findAllFeeds(Long userId, Pageable pageable);
}
