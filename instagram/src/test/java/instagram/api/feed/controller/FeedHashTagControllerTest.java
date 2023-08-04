package instagram.api.feed.controller;

import instagram.api.feed.service.FeedHashTagService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FeedHashTagControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedHashTagService feedHashTagService;
    @Autowired FeedImageRepository feedImageRepository;

    @BeforeEach
    void init(){
        List<String> list = Arrays.asList("가", "나", "다", "라", "마", "바", "사", "아", "자", "10");
        for(int i=0; i<10; i++){
            Feed feed = new Feed();
            FeedImage feedImage = FeedImage.builder().feedImgUrl("koq.com").feed(feed).build();
            HashTag hashTag = new HashTag(list.get(i));

            feedRepository.save(feed);
            hashTagRepository.save(hashTag);
            feedImageRepository.save(feedImage);

            feedHashTagService.tagging(feed.getId(), hashTag.getId());
        }

        for(int i=1; i<=9; i++){
            feedHashTagService.tagging(10L-i, i + 0L);
        }
    }

    @Test
    @DisplayName("해시태그 피드 목록 테스트")
    void hashFeed() throws Exception {
        mockMvc.perform(get("/api/feed/tags/가"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}