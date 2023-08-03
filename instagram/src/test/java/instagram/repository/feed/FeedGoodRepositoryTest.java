package instagram.repository.feed;

import instagram.api.feed.service.FeedGoodService;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.user.User;
import instagram.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FeedGoodRepositoryTest {

    @Autowired FeedRepository feedRepository;
    @Autowired UserRepository userRepository;
    @Autowired FeedGoodRepository feedGoodRepository;
    @Autowired FeedGoodService feedGoodService;

    @BeforeEach
    void before(){
        Feed feed = new Feed();
        User user = new User();
        feedRepository.save(feed);
        userRepository.save(user);
        feedGoodService.like(1L, 1L);
    }
    @Test
    @DisplayName("유저와 피드로 좋아요 찾기")
    void test(){
        Feed feed = new Feed();
        User user = new User();
        feedRepository.save(feed);
        userRepository.save(user);
        FeedGood save = feedGoodService.like(feed.getId(), user.getId());

        Feed findFeed = feedRepository.findById(feed.getId()).get();
        User findUser = userRepository.findById(user.getId()).get();

        FeedGood findFeedGood = feedGoodRepository.findByFeedAndUser(findFeed, findUser);

        Assertions.assertThat(save).isEqualTo(findFeedGood);
    }
}