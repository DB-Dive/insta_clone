package instagram.repository.feed;

import instagram.api.feed.service.FeedHashTagService;
import instagram.entity.feed.Feed;
import instagram.entity.feed.HashTag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FeedHashTagRepositoryTest {

    @Autowired FeedHashTagRepository feedHashTagRepository;
    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedHashTagService feedHashTagService;

    @BeforeEach
    void before(){
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for(int i=0; i<10; i++){
            Feed feed = new Feed().builder().build();
            HashTag hashTag = new HashTag(list.get(i));

            feedRepository.save(feed);
            hashTagRepository.save(hashTag);

            feedHashTagService.tagging(feed.getId(), hashTag.getId());
        }

        for(int i=1; i<=9; i++){
            feedHashTagService.tagging(10L-i, i + 0L);
        }

        Feed feed = new Feed().builder().build();
        HashTag hashTag = new HashTag("5");

        feedRepository.save(feed);
        hashTagRepository.save(hashTag);

        feedHashTagService.tagging(feed.getId(), hashTag.getId());
    }

    @Test
    @DisplayName("태그가 붙은 게시물 조회")
    void test(){
        // findFeedByTagId
        List<Feed> feedByTagName = feedHashTagRepository.findFeedByTagname("5");

        for (Feed feed : feedByTagName) {
            System.out.println("feed = " + feed.getId());
        }

        Assertions.assertThat(feedByTagName.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("태그가 붙은 게시물 최신순 조회")
    void test2(){
        List<Feed> list = feedHashTagRepository.findFeedByTagnameOrderByCreateAtDesc("5");

        for (Feed feed : list) {
            System.out.println("feed.getCreatedAt() = " + feed.getCreatedAt());
        }

        Assertions.assertThat(list.size()).isEqualTo(3);
        LocalDateTime t1 = list.get(0).getCreatedAt();
        LocalDateTime t2 = list.get(1).getCreatedAt();
        Assertions.assertThat(t1).isAfter(t2);
    }
}