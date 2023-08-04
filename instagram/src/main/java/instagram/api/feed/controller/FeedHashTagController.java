package instagram.api.feed.controller;

import instagram.api.feed.response.HashTagResponse;
import instagram.api.feed.service.FeedHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedHashTagController {

    private final FeedHashTagService feedHashTagService;

    @GetMapping("/api/feed/tags/{tagName}")
    public HashTagResponse tags(@PathVariable String tagName){
        return feedHashTagService.taggingFeedList(tagName);
    }
}
