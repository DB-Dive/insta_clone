package instagram.api.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public String fullView(@RequestParam int page, @RequestParam int size, @RequestParam Long userId) {
        feedService.fullView(page, size, userId);
        return "full view";
    }
    @GetMapping(value = "/{feedId}")
    public SelectViewResponse selectView(@PathVariable Long feedId, @RequestParam int cmtPage, @RequestParam int cmtSize, @RequestParam Long userId) {
        return feedService.selectView(feedId, cmtPage, cmtSize, userId);
    }
}
