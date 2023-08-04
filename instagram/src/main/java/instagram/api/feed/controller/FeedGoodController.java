package instagram.api.feed.controller;

import instagram.api.feed.dto.request.FeedGoodRequest;
import instagram.api.feed.dto.response.FeedGoodResponse;
import instagram.api.feed.dto.response.GoodUserResponse;
import instagram.api.feed.service.FeedGoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedGoodController {

    private final FeedGoodService feedGoodService;

    @PostMapping("/good")
//    public GoodResponse like(@AuthenticationPrincipal User user, @RequestBody Long feedId){
    public FeedGoodResponse like(@RequestBody FeedGoodRequest request){
        // TODO 유저 정보 받아와서 변경

        feedGoodService.like(request.getFeedId(), request.getUserId());
        return new FeedGoodResponse(feedGoodService.countByFeedId(request.getFeedId()));
    }

    @DeleteMapping("/good")
    public FeedGoodResponse dislike(@RequestBody FeedGoodRequest request){
        // TODO 유저 정보 받아와서 변경

        feedGoodService.dislike(request.getFeedId(), request.getUserId());
        return new FeedGoodResponse(feedGoodService.countByFeedId(request.getFeedId()));
    }

    @GetMapping("/{feedId}/good")
    public GoodUserResponse likeUsers(@PathVariable Long feedId){
        return feedGoodService.findUsers(feedId);
    }
}
