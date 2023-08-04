package instagram.api.feed.service;

import instagram.entity.comment.Comment;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedImage;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.feed.HashTagRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final CommentRepository commentRepository;
    private final FeedImageRepository feedImageRepository;

    public String getFirstImgUrl(Long feedId){
        List<FeedImage> feedImages = feedImageRepository.findAllByFeedId(feedId);
        return feedImages.get(0).getFeedImgUrl();
    }

    public int getCommentCount(Long feedId){
        return commentRepository.countByFeedId(feedId);
    }
}
