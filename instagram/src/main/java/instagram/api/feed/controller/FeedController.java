package instagram.api.feed.controller;

import instagram.api.feed.dto.request.FeedEditRequest;
import instagram.api.feed.dto.response.SelectViewResponse;
import instagram.api.feed.service.FeedService;
import instagram.config.auth.LoginUser;
import instagram.entity.feed.Feed;
import instagram.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping(value = "/{feedId}")
    public SelectViewResponse selectView(@PathVariable Long feedId, @RequestParam int cmtPage, @RequestParam int cmtSize, @RequestParam Long userId) {
        return feedService.selectView(feedId, cmtPage, cmtSize, userId);
    }

    @PutMapping("/{feedId}")
    public void edit(@PathVariable Long feedId, @RequestBody FeedEditRequest request){
        feedService.edit(feedId, request.getTags(), request.getContent());
    }

    @DeleteMapping("/{feedId}")
    public void delete(@PathVariable Long feedId){
        feedService.delete(feedId);
    }
}
