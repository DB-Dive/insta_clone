package instagram.api.feed.service;

import instagram.entity.comment.Comment;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.feed.HashTag;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FeedServiceTest {

    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedImageRepository feedImageRepository;
    @Autowired FeedService feedService;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    void init(){
        for(int i=0; i<10; i++){
            Feed feed = new Feed();
            Comment comment = Comment.builder().feed(feed).build();
            FeedImage feedImage = FeedImage.builder().feedImgUrl("koq.com"+i).feed(feed).build();

            feedRepository.save(feed);
            commentRepository.save(comment);
            feedImageRepository.save(feedImage);
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
        Assertions.assertThat(firstImgUrl).isEqualTo("koq.com0");

        String firstImgUrl2 = feedService.getFirstImgUrl(2L);
        Assertions.assertThat(firstImgUrl2).isEqualTo("koq.com1");
    }

    @Test
    @DisplayName("피드 ID로 댓글 갯수 추출")
    void test2(){
        int commentCount = feedService.getCommentCount(1L);
        Assertions.assertThat(commentCount).isEqualTo(3);

        int commentCount2 = feedService.getCommentCount(2L);
        Assertions.assertThat(commentCount2).isEqualTo(1);
    }
}