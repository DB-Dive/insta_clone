package instagram.api.feed.controller;

import instagram.api.feed.service.FeedGoodService;
import instagram.config.auth.LoginUser;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

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

        LoginUser loginUser = new LoginUser(user);
        LoginUser loginUser2 = new LoginUser(user2);
        LoginUser loginUser3 = new LoginUser(user3);

        feedGoodService.like(1L,loginUser);
        feedGoodService.like(1L,loginUser2);
        feedGoodService.like(1L,loginUser3);

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("like 요청 시 응답으로 좋아요 개수를 반환해준다.")
    void likeTest() throws Exception {
        mockMvc.perform(post("/api/feed/good/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodCnt").value(4))
                .andDo(print());

    }

    @Test
    @DisplayName("dislike 응답으로 좋아요 개수를 반환해준다.")
    void dislikeTest() throws Exception {
        mockMvc.perform(delete("/api/feed/good/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodCnt").value(2))
                .andDo(print());

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