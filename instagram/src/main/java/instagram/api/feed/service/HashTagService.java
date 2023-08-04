package instagram.api.feed.service;

import instagram.api.feed.dto.TestDto;
import instagram.api.feed.dto.response.HashTagResponse;
import instagram.entity.feed.Feed;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    // 정신 나간 코드
//    public HashTagResponse taggingFeedList(String tagName){
//        List<Feed> feeds = feedHashTagRepository.findFeedByTagnameOrderByCreateAtDesc(tagName);
//        List<HashTagResponse.Res> list = new ArrayList<>();
//        int size = feeds.size() > 9 ? 9 : feeds.size();
//        for (int i=0; i<size; i++) {
//            Feed feed = feeds.get(i);
//
//            list.add(
//                    HashTagResponse.Res.builder()
//                            .id(feed.getId())
//                            .feedImage(feedService.getFirstImgUrl(feed.getId()))
//                            .goodCnt(feedGoodService.countByFeedId(feed.getId()))
//                            .commentCnt(feedService.getCommentCount(feed.getId()))
//                            .build()
//            );
//        }
//
//        return HashTagResponse.builder()
//                .tagname(tagName)
//                .feedCount(feeds.size())
//                .feed(list)
//                .build();
//    }
}
