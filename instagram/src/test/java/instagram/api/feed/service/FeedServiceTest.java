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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
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
            Feed feed = Feed.builder().content(i+"번째 내용").build();
            Comment comment = Comment.builder().feed(feed).build();
            FeedImage feedImage = FeedImage.builder().feedImgUrl("koq.com"+i).feed(feed).build();

            feedRepository.save(feed);
            commentRepository.save(comment);
            feedImageRepository.save(feedImage);

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
        feedService.edit(1L, hash, "내용이 바꼈어용");
        Feed feed = feedRepository.findById(1L).get();

        System.out.println(feed.getId());
        System.out.println(feed.getContent());
        List<HashTag> allByFeedId = hashTagRepository.findAllByFeedId(1L);
        List<String> tagnames = allByFeedId.stream().map(HashTag::getTagname).collect(Collectors.toList());

        assertThat(feed.getContent()).isEqualTo("내용이 바꼈어용");
        assertThat(tagnames).contains("해 시", "태 그 ", "하", "록");
    }
}