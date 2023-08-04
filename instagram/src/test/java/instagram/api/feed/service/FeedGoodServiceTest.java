package instagram.api.feed.service;

import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.user.User;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FeedGoodServiceTest {

    @Autowired FeedRepository feedRepository;
    @Autowired UserRepository userRepository;
    @Autowired FeedGoodRepository feedGoodRepository;
    @Autowired FeedGoodService feedGoodService;

    @BeforeEach
    void before(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();

        Feed feed1 = new Feed();
        Feed feed2 = new Feed();
        Feed feed3 = new Feed();
        Feed feed4 = new Feed();
        Feed feed5 = new Feed();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        feedRepository.save(feed1);
        feedRepository.save(feed2);
        feedRepository.save(feed3);
        feedRepository.save(feed4);
        feedRepository.save(feed5);

        feedGoodService.like(feed1.getId(), user1.getId());
        feedGoodService.like(feed2.getId(), user1.getId());
        feedGoodService.like(feed2.getId(), user2.getId());
        feedGoodService.like(feed1.getId(), user2.getId());
        feedGoodService.like(feed3.getId(), user3.getId());
        feedGoodService.like(feed4.getId(), user4.getId());
        feedGoodService.like(feed5.getId(), user5.getId());
    }

    @Test
    @DisplayName("DB 저장 테스트")
    void test(){
        User user = new User();
        Feed feed = new Feed();

        userRepository.save(user);
        feedRepository.save(feed);

        Long before = feedGoodRepository.count();

        feedGoodService.like(user.getId(), feed.getId());

        assertThat(feedGoodRepository.count()).isEqualTo(before+1);
    }
    @Test
    @DisplayName("피드ID 좋아요 수 조회 테스트")
    void test2(){
        User user = new User();
        User user2 = new User();
        Feed feed = new Feed();
        Feed feed2 = new Feed();
        FeedGood feedGood = FeedGood.builder().user(user).feed(feed).build();
        FeedGood feedGood2 = FeedGood.builder().user(user).feed(feed2).build();
        FeedGood feedGood3 = FeedGood.builder().user(user2).feed(feed2).build();

        feedRepository.save(feed);
        feedRepository.save(feed2);
        userRepository.save(user);
        userRepository.save(user2);
        feedGoodRepository.save(feedGood);
        feedGoodRepository.save(feedGood2);
        feedGoodRepository.save(feedGood3);

        assertThat(feedGoodService.countByFeedId(feed2.getId())).isEqualTo(2);
    }

    @Test
    @DisplayName("DB 삭제 테스트")
    void test3(){
        Long beforeCnt = feedGoodRepository.count();

        Feed feed = feedRepository.findById(1L).get();
        User user = userRepository.findById(2L).get();

        feedGoodService.dislike(feed.getId(), user.getId());

        Long afterCnt = feedGoodRepository.count();

        assertThat(afterCnt).isEqualTo(beforeCnt-1);
    }

    @Test
    @DisplayName("게시물을 좋아요 한 유저들 데려오기")
    void test4(){
        List<User> find = feedGoodRepository.findUserByFeedId(2L);
        for (User user : find) {
            System.out.println("user = " + user.getId());
        }
        User userOne = userRepository.findById(1L).get();

        assertThat(find.size()).isEqualTo(2);
        assertThat(find).contains(userOne);
    }

    @Test
    @Rollback(false)
    void test5(){
        Feed feed = feedRepository.findById(1L).get();
        System.out.println("feed = " + feed);
        feedRepository.delete(feed);
    }
}