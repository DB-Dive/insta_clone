package instagram.api.feed.controller;

import instagram.api.feed.dto.response.FeedGoodResponse;
import instagram.api.feed.dto.response.GoodUserResponse;
import instagram.api.feed.service.FeedGoodService;
import instagram.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedGoodController {

    private final FeedGoodService feedGoodService;

    @PostMapping("/good/{feedId}")
    public FeedGoodResponse like(@PathVariable Long feedId, @AuthenticationPrincipal LoginUser loginUser){
        System.out.println(loginUser.getUser().getId());
        feedGoodService.like(feedId, loginUser);
        return new FeedGoodResponse(feedGoodService.countByFeedId(feedId));
    }

    @DeleteMapping("/good/{feedId}")
    public FeedGoodResponse dislike(@PathVariable Long feedId, @AuthenticationPrincipal LoginUser loginUser){
        feedGoodService.dislike(feedId, loginUser);
        return new FeedGoodResponse(feedGoodService.countByFeedId(feedId));
    }

    @GetMapping("/{feedId}/good")
    public GoodUserResponse likeUsers(@PathVariable Long feedId){
        return feedGoodService.findUsers(feedId);
    }
}
