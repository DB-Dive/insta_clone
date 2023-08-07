package instagram.api.feed.controller;

import instagram.api.feed.dto.request.FeedEditRequest;
import instagram.api.feed.dto.response.SelectViewResponse;
import instagram.api.feed.dto.response.TotalViewResponse;
import instagram.api.feed.service.FeedService;
import instagram.config.auth.LoginUser;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.feed.FeedImage;
import instagram.entity.user.Follow;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.BookmarkRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.FollowRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping(value = "/{feedId}")
    public SelectViewResponse selectView(@PathVariable Long feedId, @RequestParam int cmtPage, @RequestParam int cmtSize, @RequestParam Long userId) {
        return feedService.selectView(feedId, cmtPage, cmtSize, userId);
    }

    @GetMapping("/total")
    public String totalView(@RequestParam Long userId) {
        feedService.totalView(userId);
        return "ha~";
    }

    @PutMapping("/{feedId}")
    public void edit(@PathVariable Long feedId, @RequestBody FeedEditRequest request){
        feedService.edit(feedId, request.getTags(), request.getContent());
    }

    @DeleteMapping("/{feedId}")
    public void delete(@PathVariable Long feedId){
        feedService.delete(feedId);
    }

    // TODO: 2023-08-04 지워도댐

//    private final int userNum = 5;
//    private final int feedNum = 10;
//    private final int bookmarkNum = 20;
//    private final int feedGoodNum = 30;
//    private final int commentNum = 50;
//    private final int feedImageNum = 30;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private FeedRepository feedRepository;
//    @Autowired
//    private BookmarkRepository bookmarkRepository;
//    @Autowired
//    private FeedGoodRepository feedGoodRepository;
//    @Autowired
//    private CommentRepository commentRepository;
//    @Autowired
//    private FeedImageRepository feedImageRepository;
//    @Autowired
//    private FollowRepository followRepository;
//    @GetMapping(value = "/init")
//    public String init() {
//        for(int i=1; i<=userNum; i++) {
//            userRepository.save(User.builder()
//                    .username("username" + i)
//                    .nickname("nickname" + i)
//                    .email("usernaem" + i + "@naver.com")
//                    .password("password" + i)
//                    .build());
//        }
//
//        for(int i=1; i<=feedNum; i++) {
//            feedRepository.save(Feed.builder()
//                    .content("my name is username" + i)
//                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
//                    .build());
//        }
//
//        for(int i=1; i<=bookmarkNum; i++) {
//            Long feedId = (long)(Math.random()*feedNum+1);
//            Long userId = (long)(Math.random()*userNum+1);
//
//            bookmarkRepository.save(Bookmark.builder()
//                    .feed(feedRepository.findById(feedId).get())
//                    .user(userRepository.findById(userId).get())
//                    .build());
//        }
//
//        for(int i=1; i<=feedGoodNum; i++) {
//            feedGoodRepository.save(FeedGood.builder()
//                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
//                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
//                    .build());
//        }
//
//        for(int i=1; i<=commentNum; i++) {
//            commentRepository.save(Comment.builder()
//                    .content("comment" + i)
//                    .modifiedAt(LocalDateTime.now())
//                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
//                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
//                    .build());
//        }
//
//        for(int i=1; i<=commentNum; i++) {
//            commentRepository.save(Comment.builder()
//                    .content("comment" + i)
//                    .modifiedAt(LocalDateTime.now())
//                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
//                    .user(userRepository.findById((long)(Math.random()*userNum+1)).get())
//                    .build());
//        }
//
//        for(int i=1; i<=feedImageNum; i++) {
//            feedImageRepository.save(FeedImage.builder()
//                    .feedImgUrl("feedImgUrl" + i)
//                    .feed(feedRepository.findById((long)(Math.random()*feedNum+1)).get())
//                    .build());
//        }
//
//        for(Long i=1L; i<=5L; i++) {
//            for(Long j=1L; j<=3L; j++) {
//                followRepository.save(new Follow(
//                        userRepository.findById(i).get(),
//                        userRepository.findById(j).get())
//                );
//            }
//        }
//
//        return "init";
//    }
}
