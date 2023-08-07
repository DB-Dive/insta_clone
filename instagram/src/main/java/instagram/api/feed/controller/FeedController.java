package instagram.api.feed.controller;

import instagram.api.feed.dto.request.FeedEditRequest;
import instagram.api.feed.dto.request.FeedPostRequest;
import instagram.api.feed.dto.response.SelectViewResponse;
import instagram.api.feed.dto.response.TotalViewResponse;
import instagram.api.feed.service.FeedService;
import instagram.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Slf4j
public class FeedController {

    private final FeedService feedService;

    @GetMapping(value = "/{feedId}")
    public SelectViewResponse selectView(@PathVariable Long feedId, @RequestParam int cmtPage, @RequestParam int cmtSize, @RequestParam Long userId) {
        return feedService.selectView(feedId, cmtPage, cmtSize, userId);
    }

    @GetMapping
    public TotalViewResponse totalView(@AuthenticationPrincipal LoginUser loginUser, Pageable pageable) {
        return feedService.totalView(loginUser.getUser(), pageable);
    }

    @PutMapping("/{feedId}")
    public void edit(@PathVariable Long feedId, @RequestBody FeedEditRequest request, @AuthenticationPrincipal LoginUser loginUser){
        feedService.edit(feedId, request.getTags(), request.getContent(), loginUser.getUser());
    }

    @DeleteMapping("/{feedId}")
    public void delete(@PathVariable Long feedId, @AuthenticationPrincipal LoginUser loginUser){
        feedService.delete(feedId, loginUser.getUser());
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public void post(
            @RequestPart(value = "feedPostRequest") FeedPostRequest feedPostRequest,
            @RequestPart(value = "image") List<MultipartFile> image,
            @AuthenticationPrincipal LoginUser loginUser
    ){
        feedService.post(feedPostRequest, image, loginUser.getUser());
    }

}
