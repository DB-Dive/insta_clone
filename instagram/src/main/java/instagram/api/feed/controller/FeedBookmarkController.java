package instagram.api.feed.controller;

import instagram.api.feed.dto.BookmarkDto;
import instagram.api.feed.dto.BookmarkFeedDto;
import instagram.api.feed.service.FeedBookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/feed/bookmarks")
public class FeedBookmarkController {

    @Autowired
    FeedBookmarkService feedBookmarkService;

    @PostMapping
    public void bookmark(@RequestBody BookmarkDto bookmarkDto) {
        feedBookmarkService.bookmark(bookmarkDto);
    }

    @DeleteMapping
    public void bookmarkCancel(@RequestBody BookmarkDto bookmarkDto) {
        feedBookmarkService.bookmarkCancel(bookmarkDto);
    }

    @GetMapping
    public List<BookmarkFeedDto> bookmarkView(@RequestParam int page, @RequestParam int size, @RequestParam Long userId) {
        return feedBookmarkService.bookmarkView(page, size, userId);
    }
}
