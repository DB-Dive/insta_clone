package instagram.api.feed.service;

import instagram.api.feed.dto.response.GoodUserResponse;
import instagram.config.auth.LoginUser;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedGoodService {

    private final FeedGoodRepository feedGoodRepository;
    private final FeedRepository feedRepository;

    public FeedGood like(Long feedId, LoginUser loginUser) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시물입니다."));
        FeedGood feedGood = FeedGood.builder()
                .feed(feed)
                .user(loginUser.getUser())
                .build();
        feedGoodRepository.save(feedGood);

        return feedGood;
    }

    public void dislike(Long feedId, LoginUser loginUser){
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        FeedGood find = feedGoodRepository.findByFeedAndUser(feed, loginUser.getUser());

        feedGoodRepository.delete(find);
    }

    public int countByFeedId(Long feedId){
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        return feedGoodRepository.countByFeed(feed);
    }

    public GoodUserResponse findUsers(Long feedId){
        List<GoodUserResponse.UserDto> users = feedGoodRepository.findUserByFeedId(feedId, PageRequest.of(0, 30))
                .stream()
                .map(user -> new GoodUserResponse.UserDto(user))
                .collect(Collectors.toList());

        return new GoodUserResponse(users);
    }
}
