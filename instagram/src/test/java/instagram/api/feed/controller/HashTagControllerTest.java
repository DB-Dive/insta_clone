package instagram.api.feed.controller;

import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.feed.HashTag;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HashTagControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedImageRepository feedImageRepository;

    @BeforeEach
    void init(){
        for(int i=1; i<=10; i++){
            Feed feed = new Feed();
            FeedImage feedImage = FeedImage.builder().feed(feed).feedImgUrl("aa").build();
            HashTag hashTag = HashTag.builder().tagname("사과"+i).feedId(i+0L).build();
            HashTag hashTag2 = HashTag.builder().tagname("사과"+i).feedId(10-0L).build();

            feedRepository.save(feed);
            feedImageRepository.save(feedImage);
            hashTagRepository.save(hashTag);
            hashTagRepository.save(hashTag2);
        }
    }

    @Test
    @DisplayName("해시태그 목록 조회")
    void test() throws Exception {
        mockMvc.perform(get("/api/feed/tags/사과1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}