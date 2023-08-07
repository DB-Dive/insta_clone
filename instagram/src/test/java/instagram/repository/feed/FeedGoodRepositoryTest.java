package instagram.repository.feed;

import instagram.api.feed.dto.response.FeedDto;
import instagram.api.feed.dto.response.GoodUserResponse;
import instagram.api.feed.service.FeedGoodService;
import instagram.config.auth.LoginUser;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.feed.FeedImage;
import instagram.entity.user.Follow;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.user.FollowRepository;
import instagram.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional
class FeedGoodRepositoryTest {

    @Autowired FeedRepository feedRepository;
    @Autowired UserRepository userRepository;
    @Autowired FeedGoodRepository feedGoodRepository;
    @Autowired FeedGoodService feedGoodService;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private FeedImageRepository feedImageRepository;

    @BeforeEach
    void before(){
        User user = new User();
        user.setUsername("aaa");
        userRepository.save(user);

        User following = new User();
        following.setUsername("aaa2");
        userRepository.save(following);

        User following2 = new User();
        following2.setUsername("aaa3");
        userRepository.save(following2);

        User unfollow = new User();
        unfollow.setUsername("언팔");
        userRepository.save(unfollow);

        Follow follow = new Follow(user, following);
        Follow follow2 = new Follow(user, following2);
        followRepository.save(follow);
        followRepository.save(follow2);

        Feed feed = Feed.builder()
                .user(following)
                .content("가나다")
                .build();
        Feed feed2 = Feed.builder()
                .user(following2)
                .content("가나다2")
                .build();
        Feed feed3 = Feed.builder()
                .user(following2)
                .content("가나다3")
                .build();
        Feed feed4 = Feed.builder()
                .user(following2)
                .content("가나다4")
                .build();
        Feed feed5 = Feed.builder()
                .user(unfollow)
                .content("가나다5")
                .build();
        feedRepository.save(feed);
        feedRepository.save(feed2);
        feedRepository.save(feed3);
        feedRepository.save(feed4);
        feedRepository.save(feed5);

        FeedImage 이미지1 = FeedImage.builder()
                .feedImgUrl("이미지1")
                .feed(feed)
                .build();
        FeedImage 이미지2 = FeedImage.builder()
                .feedImgUrl("이미지2")
                .feed(feed)
                .build();
        feedImageRepository.save(이미지1);
        feedImageRepository.save(이미지2);

        Comment 댓글1 = new Comment("댓글1", feed, user);
        Comment 댓글2 = new Comment("댓글2", feed2, user);
        Comment 댓글3 = new Comment("댓글3", feed2, user);
        Comment 댓글4 = new Comment("댓글3", feed3, user);
        commentRepository.save(댓글1);
        commentRepository.save(댓글2);
        commentRepository.save(댓글3);
        commentRepository.save(댓글4);

        FeedGood feedGood = FeedGood.builder()
                .feed(feed)
                .user(user)
                .build();
        FeedGood feedGood2 = FeedGood.builder()
                .feed(feed)
                .user(following2)
                .build();
        feedGoodRepository.save(feedGood);
        feedGoodRepository.save(feedGood2);

        Bookmark book = Bookmark.builder()
                .feed(feed)
                .user(user)
                .build();
        bookmarkRepository.save(book);


//        LoginUser loginUser = new LoginUser(user);
//        feedGoodService.like(1L, loginUser);
    }
    @Test
    @DisplayName("유저와 피드로 좋아요 찾기")
    void test(){
        Feed feed = new Feed();
        User user = new User();
        feedRepository.save(feed);
        userRepository.save(user);

        LoginUser loginUser = new LoginUser(user);

        FeedGood save = feedGoodService.like(feed.getId(), loginUser);

        Feed findFeed = feedRepository.findById(feed.getId()).get();
        User findUser = userRepository.findById(user.getId()).get();

        FeedGood findFeedGood = feedGoodRepository.findByFeedAndUser(findFeed, findUser);

        Assertions.assertThat(save).isEqualTo(findFeedGood);
    }

    @Test
    void test2() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        PageImpl<FeedDto> allFeeds = feedRepository.findAllFeeds(1L, pageable);
        for (FeedDto allFeed : allFeeds) {
            System.out.println("allFeed = " + allFeed);
        }

        // when

        // then

    }
}
