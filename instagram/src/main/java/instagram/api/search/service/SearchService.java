package instagram.api.search.service;

import instagram.api.search.response.OnlyTagResponse;
import instagram.api.search.response.SearchResponse;
import instagram.api.search.response.SearchTagResponse;
import instagram.api.search.response.SearchUserResponse;
import instagram.repository.feed.HashTagRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final HashTagRepository hashTagRepository;
    private final UserRepository userRepository;

    public SearchResponse search(String keyword){
        List<SearchUserResponse> users = userRepository.findByNickname(keyword, PageRequest.of(0, 30));
        List<SearchTagResponse> tags = hashTagRepository.findByKeyword(keyword, PageRequest.of(0, 30));

        return SearchResponse.builder()
                .users(users)
                .tags(tags)
                .build();
    }

    public OnlyTagResponse searchTag(String keyword) {
        return new OnlyTagResponse(hashTagRepository.findByKeyword(keyword, PageRequest.of(0, 30)));
    }
}
