package instagram.api.feed.controller;

import instagram.api.feed.dto.BookmarkDto;
import instagram.api.feed.dto.BookmarkFeedDto;
import instagram.api.feed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/feed/bookmarks")
public class FeedController {

    @Autowired
    FeedService feedService;

    @PostMapping
    public void bookmark(@RequestBody BookmarkDto bookmarkDto) {
        feedService.bookmark(bookmarkDto);
    }

    @DeleteMapping
    public void bookmarkCancel(@RequestBody BookmarkDto bookmarkDto) {
        feedService.bookmarkCancel(bookmarkDto);
    }

    @GetMapping
    public List<BookmarkFeedDto> bookmarkView(@RequestParam int page, @RequestParam int size, @RequestParam Long userId) {
        return feedService.bookmarkView(page, size, userId);
    }
}
