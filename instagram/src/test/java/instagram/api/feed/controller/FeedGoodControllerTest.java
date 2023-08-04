package instagram.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import instagram.api.feed.request.FeedGoodRequest;
import instagram.api.feed.service.FeedGoodService;
import instagram.entity.feed.Feed;
import instagram.entity.user.User;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class FeedGoodControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired FeedRepository feedRepository;
    @Autowired UserRepository userRepository;
    @Autowired FeedGoodRepository feedGoodRepository;
    @Autowired FeedGoodService feedGoodService;

    @BeforeEach
    void before(){
        feedGoodRepository.deleteAllInBatch();

        Feed feed = new Feed();
        Feed feed2 = new Feed();
        feedRepository.save(feed);
        feedRepository.save(feed2);

        User user = new User();
        User user2 = new User();
        User user3 = new User();
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        feedGoodService.like(1L,1L);
        feedGoodService.like(1L,2L);
        feedGoodService.like(1L,3L);
    }

    @Test
    @DisplayName("like 요청 시 응답으로 좋아요 개수를 반환해준다.")
    void likeTest() throws Exception {
        FeedGoodRequest request = new FeedGoodRequest(2L, 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/feed/good")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodCnt").value(1))
                .andDo(print());

        assertThat(feedGoodRepository.count()).isEqualTo(4);
    }

    @Test
    @DisplayName("dislike 응답으로 좋아요 개수를 반환해준다.")
    void dislikeTest() throws Exception {
        FeedGoodRequest request = new FeedGoodRequest(1L, 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(delete("/api/feed/good")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodCnt").value(2))
                .andDo(print());

        assertThat(feedGoodRepository.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("좋아요 유저 목록 테스트")
    void user() throws Exception {
        mockMvc.perform(get("/api/feed/1/good"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.[0].userId").value(1))
                .andExpect(jsonPath("$.users.[1].userId").value(2))
                .andExpect(jsonPath("$.users.[2].userId").value(3))
                .andDo(print());
    }
}