package instagram.repository.feed;

import instagram.api.feed.dto.TestDto;
import instagram.api.feed.dto.response.HashTagResponse;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.feed.HashTag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashTagRepositoryTest {

    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedImageRepository feedImageRepository;

    @BeforeEach
    void init(){
        for(int i=1; i<=10; i++){
            Feed feed = new Feed();
            HashTag hashTag = HashTag.builder().tagname("힘들다"+i).feedId(i+0L).build();
            FeedImage feedImage = FeedImage.builder().feedImgUrl("feedId "+i).feed(feed).build();
            FeedImage feedImage2 = FeedImage.builder().feedImgUrl("feedId "+i).feed(feed).build();
            FeedImage feedImage3 = FeedImage.builder().feedImgUrl("feedId "+i).feed(feed).build();

            // feedId = 1 || 1,2,3
            // feedId = 2 || 4,5,6
            // feedId = 3 || 7,8,9

            feedRepository.save(feed);
            feedImageRepository.save(feedImage);
            feedImageRepository.save(feedImage2);
            feedImageRepository.save(feedImage3);
            hashTagRepository.save(hashTag);
        }

        HashTag hashTag = HashTag.builder().tagname("힘들다"+0).feedId(0+3L).build();
        HashTag hashTag2 = HashTag.builder().tagname("힘들다"+0).feedId(0+5L).build();
        hashTagRepository.save(hashTag);
        hashTagRepository.save(hashTag2);
    }

    @Test
    @DisplayName("태그가 붙은 게시물ID 조회")
    void test() {
        List<Long> ids = hashTagRepository.findAllFeedIdByTagname("힘들다2");
        System.out.println("ids = " + ids);
    }
    
    @Test
    void test2(){
        List<Feed> feeds = hashTagRepository.find("힘들다0");
        for (Feed feed : feeds) {
            System.out.println("feed = " + feed.getId());
        }
    }

    @Test
    void test3(){
        List<TestDto> list = hashTagRepository.dopeQuery("힘들다0");
        for (TestDto testDto : list) {
            System.out.println("testDto.toString() = " + testDto.toString());
        }
    }

    @Test
    void test4(){
        Long before = hashTagRepository.count();
        hashTagRepository.deleteAllByFeedId(1L);
        Long after = hashTagRepository.count();

        Assertions.assertThat(before-1).isEqualTo(after);
    }
}