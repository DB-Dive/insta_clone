package instagram.api.feed.controller;

import instagram.api.feed.dto.response.HashTagResponse;
import instagram.api.feed.service.HashTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping("/api/feed/tags/{tagName}")
    public HashTagResponse taggingFeeds(@PathVariable String tagName){
        return hashTagService.getFeed(tagName);
    }
}
