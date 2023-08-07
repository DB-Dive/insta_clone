package instagram.api.search.controller;

import instagram.api.search.service.SearchService;
import instagram.entity.feed.Feed;
import instagram.entity.feed.HashTag;
import instagram.entity.user.User;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SearchControllerTest {

    @Autowired SearchService searchService;
    @Autowired UserRepository userRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedRepository feedRepository;
    @Autowired MockMvc mockMvc;

    @BeforeEach
    void init(){
        for(int i=1; i<=10; i++){
            List<String> list = List.of("사과", "포도", "배", "파인애플", "농장", "사과공장", "아침사과", "백설공주사과", "복숭아", "납작복숭아농장");
            Feed feed = new Feed();
            User user = User.builder().username("과일가게"+i).nickname(list.get(i-1)).build();
            feedRepository.save(feed);
            userRepository.save(user);

            HashTag hashTag = HashTag.builder().feedId(feed.getId()).tagname("농장"+i).build();
            HashTag hashTag2 = HashTag.builder().feedId(feed.getId()).tagname("농장"+i).build();

            hashTagRepository.save(hashTag);
            hashTagRepository.save(hashTag2);
        }

        HashTag hashTag = HashTag.builder().feedId(1L).tagname("사과농장").build();
        hashTagRepository.save(hashTag);
    }

    @Test
    @DisplayName("통합 검색 테스트")
    void test() throws Exception {
        mockMvc.perform(get("/api/search/농장"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("태그 검색 테스트")
    void test2() throws Exception {
        mockMvc.perform(get("/api/search/tag/농장"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
