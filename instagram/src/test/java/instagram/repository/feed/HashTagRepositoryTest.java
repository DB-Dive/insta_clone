package instagram.repository.feed;

import instagram.api.feed.dto.TestDto;
import instagram.api.feed.dto.response.HashTagResponse;
import instagram.entity.feed.Feed;
import instagram.entity.feed.HashTag;
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

    @BeforeEach
    void init(){
        for(int i=0; i<10; i++){
            Feed feed = new Feed();
            HashTag hashTag = HashTag.builder().tagname("힘들다"+i).feedId(i+1L).build();
            feedRepository.save(feed);
            hashTagRepository.save(hashTag);
        }

        HashTag hashTag = HashTag.builder().tagname("힘들다"+0).feedId(0+3L).build();
        hashTagRepository.save(hashTag);
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
}