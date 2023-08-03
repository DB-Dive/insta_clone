package instagram.api.feed.service;

import instagram.api.feed.dto.BookmarkDto;
import instagram.api.feed.dto.BookmarkFeedDto;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Bookmark;
import instagram.entity.feed.Feed;
import instagram.entity.feed.FeedGood;
import instagram.entity.feed.FeedImage;
import instagram.repository.feed.BookmarkRepository;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FeedRepository feedRepository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void bookmark(BookmarkDto bookmarkDto) {
//        Optional<Bookmark> fb = bookmarkRepository.findByFeedIdAndUserId(bookmarkDto.getFeedId(), bookmarkDto.getUserId());
//
//        if(fb.isPresent()) {
//            throw new IllegalArgumentException("유니크");
//        }

        Bookmark newBookmark = Bookmark.builder()
                .feed(feedRepository.findById(bookmarkDto.getFeedId()).get())
                .user(userRepository.findById(bookmarkDto.getUserId()).get())
                .build();

        bookmarkRepository.save(newBookmark);
    }

    @Transactional
    public void bookmarkCancel(BookmarkDto bookmarkDto) {
        Bookmark findBookmark = bookmarkRepository.findByFeedIdAndUserId(bookmarkDto.getFeedId(), bookmarkDto.getUserId()).get();

        bookmarkRepository.delete(findBookmark);
    }

    @Transactional
    public List<BookmarkFeedDto> bookmarkView(int page, int size, Long userId) {
        List<BookmarkFeedDto> bookmarkFeedDtos = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Feed> findFeeds = bookmarkRepository.findFeedByUserId(userId, pageRequest).getContent();

        String feedImageQl = "SELECT i FROM FeedImage i JOIN i.feed f WHERE f.id IN (SELECT f FROM Bookmark b JOIN b.feed f JOIN b.user u ON u.id = :userId) ORDER BY i.id";

        List<FeedImage> findFeedImages = em.createQuery(feedImageQl, FeedImage.class)
                .setParameter("userId", userId)
                .getResultList();

        String goodCntQl = "SELECT g FROM FeedGood g JOIN g.feed f WHERE f.id IN (SELECT f FROM Bookmark b JOIN b.feed f JOIN b.user u ON u.id = :userId)";

        List<FeedGood> findGoods = em.createQuery(goodCntQl, FeedGood.class)
                .setParameter("userId", userId)
                .getResultList();

        String commentCntQl = "SELECT c FROM Comment c JOIN c.feed f WHERE f.id IN (SELECT f FROM Bookmark b JOIN b.feed f JOIN b.user u ON u.id = :userId)";

        List<Comment> findComments = em.createQuery(commentCntQl, Comment.class)
                .setParameter("userId", userId)
                .getResultList();

        for (Feed findFeed : findFeeds) {
            Long findFeedId = findFeed.getId();
            System.out.println("findFeedId = " + findFeedId);

            String feedImgUrl = null;
            for (FeedImage findFeedImage : findFeedImages) {
                if(findFeedImage.getFeed().getId() == findFeedId) {
                    feedImgUrl = findFeedImage.getFeedImgUrl();
                    break;
                }
            }

            Long goodCnt = 0L;
            for (FeedGood findGood : findGoods) {
                if(findGood.getFeed().getId() == findFeedId) {
                    goodCnt++;
                }
            }

            Long commentCnt = 0L;
            for (Comment findComment : findComments) {
                if(findComment.getFeed().getId() == findFeedId) {
                    commentCnt++;
                }
            }

            bookmarkFeedDtos.add(new BookmarkFeedDto(findFeedId, feedImgUrl, goodCnt, commentCnt));
        }

        return bookmarkFeedDtos;
    }
}
