package instagram.api.feed.service;

import instagram.api.feed.dto.CommentDto;
import instagram.api.feed.dto.CommentsDto;
import instagram.api.feed.dto.FeedImageDto;
import instagram.api.feed.dto.SelectViewResponse;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.feed.FeedImage;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.BookmarkRepository;
import instagram.repository.feed.FeedGoodRepository;
import instagram.repository.feed.FeedImageRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedService {
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedImageRepository feedImageRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FeedGoodRepository feedGoodRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void fullView(int page, int size, Long userId) {

    }

    @Transactional
    public SelectViewResponse selectView(Long feedId, int cmtPage, int cmtSize, Long userId) {
        User findUser = userRepository.findById(userId).get();

        Feed findFeed = feedRepository.findById(feedId).get();

        List<FeedImage> findFeedImages = feedImageRepository.findByFeedId(feedId);
        List<FeedImageDto> feedImageDtos = new ArrayList<>();
        for (FeedImage findFeedImage : findFeedImages) {
            feedImageDtos.add(new FeedImageDto(findFeedImage.getId(), findFeedImage.getFeedImgUrl()));
        }

        PageRequest cmtPaging = PageRequest.of(cmtPage, cmtSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> findCommentsPage = commentRepository.findByFeedId(feedId, cmtPaging);
        List<Comment> findComments = findCommentsPage.getContent();
        List<CommentDto> data = new ArrayList<>();
        for (Comment findComment : findComments) {
            data.add(CommentDto.builder()
                    .userId(findComment.getUser().getId())
                    .userProfileImage(findComment.getUser().getProfileImgUrl())
                    .username(findComment.getUser().getUsername())
                    .content(findComment.getContent())
                    .createdAt(findComment.getCreatedAt())
                    .build());
        }

        int totalPage = findCommentsPage.getTotalPages();
        int currentpage = 0; // TODO: 여기 어떻게 함??
        CommentsDto comments = new CommentsDto(data, totalPage, currentpage);

        Long goodCnt = feedGoodRepository.countByFeedId(feedId);

        boolean goodStatus;
        Optional<FeedGood> findGood = feedGoodRepository.findByUserIdAndFeedId(userId, feedId);
        if(findGood.isPresent()) {
            goodStatus = true;
        } else {
            goodStatus = false;
        }

        boolean bookmarkStatus;
        Optional<Bookmark> findBookmark = bookmarkRepository.findByUserIdAndFeedId(userId, feedId);
        if(findBookmark.isPresent()) {
            bookmarkStatus = true;
        } else {
            bookmarkStatus = false;
        }

        return SelectViewResponse.builder()
                .userId(findUser.getId())
                .userProfileImage(findUser.getProfileImgUrl())
                .username(findUser.getUsername())
                .feedId(findFeed.getId())
                .content(findFeed.getContent())
                .feedImages(feedImageDtos)
                .comments(comments)
                .goodCnt(goodCnt)
                .goodStatus(goodStatus)
                .bookmarkStatus(bookmarkStatus)
                .build();
    }
}
