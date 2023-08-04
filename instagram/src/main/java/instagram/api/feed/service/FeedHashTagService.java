package instagram.api.feed.service;

import instagram.api.feed.dto.response.HashTagResponse;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedHashTag;
import instagram.entity.feed.HashTag;
import instagram.repository.feed.FeedHashTagRepository;
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
public class FeedHashTagService {

    private final FeedRepository feedRepository;
    private final HashTagRepository hashTagRepository;
    private final FeedHashTagRepository feedHashTagRepository;
    private final FeedGoodService feedGoodService;
    private final FeedService feedService;

    public void tagging(Long feedId, Long tagId){
        Feed feed = feedRepository.findById(feedId).orElseThrow();
        HashTag hashTag = hashTagRepository.findById(tagId).orElseThrow();
        FeedHashTag tag = FeedHashTag.builder()
                        .feed(feed)
                        .hashtag(hashTag)
                        .build();

        feedHashTagRepository.save(tag);
    }

    public HashTagResponse taggingFeedList(String tagName){
        List<Feed> feeds = feedHashTagRepository.findFeedByTagnameOrderByCreateAtDesc(tagName);
        List<HashTagResponse.Res> list = new ArrayList<>();
        int size = feeds.size() > 9 ? 9 : feeds.size();
        for (int i=0; i<size; i++) {
            Feed feed = feeds.get(i);

            list.add(
                    HashTagResponse.Res.builder()
                            .id(feed.getId())
                            .feedImage(feedService.getFirstImgUrl(feed.getId()))
                            .goodCnt(feedGoodService.countByFeedId(feed.getId()))
                            .commentCnt(feedService.getCommentCount(feed.getId()))
                            .build()
            );
        }

        return HashTagResponse.builder()
                .tagname(tagName)
                .feedCount(feeds.size())
                .feed(list)
                .build();
    }
}
