package instagram.api.feed.service;

import instagram.api.feed.dto.TestDto;
import instagram.api.feed.dto.response.HashTagResponse;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;
    private final FeedGoodRepository feedGoodRepository;
    private final CommentRepository commentRepository;
    private final FeedImageRepository feedImageRepository;

    public HashTagResponse getFeed(String tagName){
        List<TestDto> feedList = hashTagRepository.dopeQuery(tagName);
        int size = feedList.size();
        int max = size > 9 ? 9 : size;

        return HashTagResponse.builder()
                .tagname(tagName)
                .feedCount(size)
                .feed(feedList.subList(0, max))
                .build();
    }

}
