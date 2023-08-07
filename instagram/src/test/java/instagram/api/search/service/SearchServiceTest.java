package instagram.api.search.service;

import instagram.api.search.response.OnlyTagResponse;
import instagram.api.search.response.SearchResponse;
import instagram.api.search.response.SearchTagResponse;
import instagram.api.search.response.SearchUserResponse;
import instagram.entity.feed.Feed;
import instagram.entity.feed.HashTag;
import instagram.entity.user.User;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import instagram.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SearchServiceTest {

    @Autowired SearchService searchService;
    @Autowired UserRepository userRepository;
    @Autowired HashTagRepository hashTagRepository;
    @Autowired FeedRepository feedRepository;

    @BeforeEach
    void init(){
        for(int i=1; i<=10; i++){
            List<String> list = List.of("사과", "포도", "배", "파인애플", "농장", "사과공장", "아침사과", "백설공주사과", "복숭아", "납작복숭아농장");
            Feed feed = new Feed();
            User user = User.builder().username(list.get(i-1)).nickname("사과농장"+i).build();
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
    void test() {
        SearchResponse response = searchService.search("공장");
        List<SearchUserResponse> users = response.getUsers();
        for (SearchUserResponse user : users) {
            System.out.println(user.toString());
        }

        List<SearchTagResponse> tags = response.getTags();
        for (SearchTagResponse tag : tags) {
            System.out.println(tag.toString());
        }
    }

    @Test
    void test2(){
        OnlyTagResponse response = searchService.searchTag("농장");
        System.out.println(response.toString());
    }

}
