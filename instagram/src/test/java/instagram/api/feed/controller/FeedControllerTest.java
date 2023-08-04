package instagram.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import instagram.api.feed.dto.request.FeedEditRequest;
import instagram.api.feed.service.FeedService;
import instagram.entity.feed.Feed;
import instagram.entity.feed.HashTag;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FeedControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired FeedService feedService;
    @Autowired FeedRepository feedRepository;
    @Autowired HashTagRepository hashTagRepository;

    @BeforeEach
    void init(){
        Feed feed = Feed.builder().content("내용 변경 전").build();
        feedRepository.save(feed);

        HashTag hashTag = HashTag.builder().tagname("하핫").feedId(feed.getId()).build();
        HashTag hashTag2 = HashTag.builder().tagname("하핫핫").feedId(feed.getId()).build();
        hashTagRepository.save(hashTag);
        hashTagRepository.save(hashTag2);
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void edit() throws Exception {
        List<String> tags = List.of("하록", "록");
        FeedEditRequest request = new FeedEditRequest(tags, "내용 변경");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/feed/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}