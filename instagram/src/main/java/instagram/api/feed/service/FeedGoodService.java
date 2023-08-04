package instagram.api.feed.service;

import instagram.api.feed.dto.response.GoodUserResponse;
import instagram.config.auth.LoginUser;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.user.User;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedGoodService {

    private final FeedGoodRepository feedGoodRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    public FeedGood like(Long feedId, LoginUser loginUser) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException());
        FeedGood feedGood = FeedGood.builder()
                .feed(feed)
                .user(loginUser.getUser())
                .build();
        feedGoodRepository.save(feedGood);

        return feedGood;
    }

    public void dislike(Long feedId, LoginUser loginUser){
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException());
        FeedGood find = feedGoodRepository.findByFeedAndUser(feed, loginUser.getUser());

        feedGoodRepository.delete(find);
    }

    public int countByFeedId(Long feedId){
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException());
        return feedGoodRepository.countByFeed(feed);
    }

    public GoodUserResponse findUsers(Long feedId){
        List<User> find = feedGoodRepository.findUserByFeedId(feedId);
        GoodUserResponse goodUserResponse = new GoodUserResponse();

        int size = find.size() > 30 ? 30 : find.size();
        for(int i=0; i<size; i++){
            User u = find.get(i);
            goodUserResponse.getUsers().add(GoodUserResponse.UserDto.builder()
                    .userId(u.getId())
                    .userProfileImage(u.getProfileImgUrl())
                    .username(u.getUsername())
                    .nickname(u.getNickname())
                    .build());
        }

        return goodUserResponse;
    }
}
