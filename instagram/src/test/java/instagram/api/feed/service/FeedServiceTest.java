package instagram.api.feed.service;

import instagram.entity.comment.Comment;
import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.feed.FeedImage;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.BookmarkRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedServiceTest {
    private final int userNum = 5;
    private final int feedNum = 10;
    private final int bookmarkNum = 20;
    private final int feedGoodNum = 30;
    private final int commentNum = 50;
    private final int feedImageNum = 30;

    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedBookmarkService feedBookmarkService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private FeedGoodRepository feedGoodRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FeedImageRepository feedImageRepository;

    @BeforeEach
    public void testInit() {
        for(int i=1; i<=userNum; i++) {
            userRepository.save(User.builder()
                    .username("username" + i)
                    .nickname("nickname" + i)
                    .email("usernaem" + i + "@naver.com")
                    .password("password" + i)
                    .build());
        }

        for(int i=1; i<=feedNum; i++) {
            feedRepository.save(Feed.builder()
                    .content("my name is username" + i)
                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
                    .build());
        }

        for(int i=1; i<=bookmarkNum; i++) {
            Long feedId = (long)(Math.random()*feedNum+1);
            Long userId = (long)(Math.random()*userNum+1);
//            Optional<Bookmark> fb = bookmarkRepository.findByFeedIdAndUserId(feedId, userId);
//
//            if(fb.isPresent()) {
//                throw new IllegalArgumentException("유니크 제약 조건 위배");
//            }

            bookmarkRepository.save(Bookmark.builder()
                    .feed(feedRepository.findById(feedId).get())
                    .user(userRepository.findById(userId).get())
                    .build());
        }

        for(int i=1; i<=feedGoodNum; i++) {
            feedGoodRepository.save(FeedGood.builder()
                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
                    .build());
        }

        for(int i=1; i<=commentNum; i++) {
            commentRepository.save(Comment.builder()
                    .content("comment" + i)
                    .modifiedAt(LocalDateTime.now())
                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
                    .build());
        }

        for(int i=1; i<=commentNum; i++) {
            commentRepository.save(Comment.builder()
                    .content("comment" + i)
                    .modifiedAt(LocalDateTime.now())
                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
                    .build());
        }

        for(int i=1; i<=feedImageNum; i++) {
            feedImageRepository.save(FeedImage.builder()
                    .feedImgUrl("feedImgUrl" + i)
                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
                    .build());
        }
    }
    @Test
    @DisplayName("select view")
    public void selectViewTest() throws Exception {
        //given

        //when
        feedService.selectView(1L, 0, 3, 1L);

        //then
    }

    @Test
    public void test1() throws Exception {
        //given

        //when

        //then
    }
}