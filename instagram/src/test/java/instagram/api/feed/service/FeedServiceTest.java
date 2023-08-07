package instagram.api.feed.service;

import instagram.config.auth.LoginUser;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.feed.HashTag;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FeedServiceTest {

    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedImageRepository feedImageRepository;
    @Autowired FeedService feedService;
    @Autowired CommentRepository commentRepository;
    @Autowired FeedGoodRepository feedGoodRepository;
    @Autowired FeedGoodService feedGoodService;
    @Autowired UserRepository userRepository;

    @BeforeEach
    void init(){
        for(int i=0; i<10; i++){
            User user = User.builder().username(i+"번째 유저").build();
            Feed feed = Feed.builder().content(i+"번째 내용").build();
            Comment comment = Comment.builder().feed(feed).build();
            FeedImage feedImage = FeedImage.builder().feedImgUrl("koq.com"+i).feed(feed).build();
            LoginUser loginUser = new LoginUser(user);

            userRepository.save(user);
            feedRepository.save(feed);
            commentRepository.save(comment);
            feedImageRepository.save(feedImage);
            feedGoodService.like(i+1L, loginUser);

            HashTag hashTag = HashTag.builder().tagname("test"+i).feedId(feed.getId()).build();
            HashTag hashTag2 = HashTag.builder().tagname("test"+i).feedId(feed.getId()).build();

            hashTagRepository.save(hashTag);
            hashTagRepository.save(hashTag2);
        }

        Feed feed = feedRepository.findById(1L).orElseThrow();
        Comment comment1 = Comment.builder().feed(feed).build();
        Comment comment2 = Comment.builder().feed(feed).build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        //feedId가 1인 경우만 댓글 3개
    }

    @Test
    @DisplayName("피드 ID로 ImgUrl 추출")
    void test(){
        String firstImgUrl = feedService.getFirstImgUrl(1L);
        assertThat(firstImgUrl).isEqualTo("koq.com0");

        String firstImgUrl2 = feedService.getFirstImgUrl(2L);
        assertThat(firstImgUrl2).isEqualTo("koq.com1");
    }

    @Test
    @DisplayName("피드 ID로 댓글 갯수 추출")
    void test2(){
        int commentCount = feedService.getCommentCount(1L);
        assertThat(commentCount).isEqualTo(3);

        int commentCount2 = feedService.getCommentCount(2L);
        assertThat(commentCount2).isEqualTo(1);
    }

    @Test
    void test3(){
        List<String> hash = List.of("해 시", "태 그 ", "하", "록");
        User user = userRepository.findById(1L).get();
        feedService.edit(1L, hash, "내용이 바꼈어용", user);
        Feed feed = feedRepository.findById(1L).get();

        System.out.println(feed.getId());
        System.out.println(feed.getContent());
        List<HashTag> allByFeedId = hashTagRepository.findAllByFeedId(1L);
        List<String> tagnames = allByFeedId.stream().map(HashTag::getTagname).collect(Collectors.toList());

        assertThat(feed.getContent()).isEqualTo("내용이 바꼈어용");
        assertThat(tagnames).contains("해 시", "태 그 ", "하", "록");
    }

    @Test
    void test4(){
        long beforeFeedCount = feedRepository.count();
        //댓글
        int beforeCommentCnt = commentRepository.countByFeedId(1L);
        //좋아요
        Long beforeGoodCnt = feedGoodRepository.countByFeedId(1L);
        //해시태그
        int beforeHashTagCnt = hashTagRepository.findAllByFeedId(1L).size();
        //피드 이미지
        Long beforeImgCnt = feedImageRepository.countByFeedId(1L);

        User user = userRepository.findById(1L).get();
        feedService.delete(1L, user);

        long afterFeedCount = feedRepository.count();
        //댓글
        int afterCommentCnt = commentRepository.countByFeedId(1L);
        //좋아요
        Long afterGoodCnt = feedGoodRepository.countByFeedId(1L);
        //해시태그
        int afterHashTagCnt = hashTagRepository.findAllByFeedId(1L).size();
        //피드 이미지
        Long afterFeedImage = feedImageRepository.countByFeedId(1L);

        assertThat(beforeFeedCount).isEqualTo(afterFeedCount+1);
        assertThat(beforeCommentCnt).isEqualTo(afterCommentCnt+3);
        assertThat(beforeGoodCnt).isEqualTo(afterGoodCnt+1);
        assertThat(beforeHashTagCnt).isEqualTo(afterHashTagCnt+2);
        assertThat(beforeImgCnt).isEqualTo(afterFeedImage+1);

        assertThat(afterCommentCnt).isEqualTo(0);
        assertThat(afterGoodCnt).isEqualTo(0);
        assertThat(afterHashTagCnt).isEqualTo(0);
        assertThat(afterFeedImage).isEqualTo(0);
    }
}
