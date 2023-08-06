package instagram.api.feed.service;

import instagram.api.feed.dto.CommentDto;
import instagram.api.feed.dto.request.FeedPostRequest;
import instagram.api.feed.dto.response.CommentsDto;
import instagram.api.feed.dto.FeedImageDto;
import instagram.api.feed.dto.response.SelectViewResponse;

import instagram.config.auth.LoginUser;
import instagram.entity.comment.Comment;
import instagram.entity.feed.*;
import instagram.entity.user.User;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.*;
import instagram.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedImageRepository feedImageRepository;
    private final CommentRepository commentRepository;
    private final FeedGoodRepository feedGoodRepository;
    private final BookmarkRepository bookmarkRepository;
    private final HashTagRepository hashTagRepository;
    private final S3Uploader s3Uploader;

    @PersistenceContext
    EntityManager em;

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


    public String getFirstImgUrl(Long feedId){
        List<FeedImage> feedImages = feedImageRepository.findAllByFeedId(feedId);
        return feedImages.get(0).getFeedImgUrl();
    }

    public int getCommentCount(Long feedId){
        return commentRepository.countByFeedId(feedId);
    }

    public void edit(Long feedId, List<String> tags, String content, User user) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시물입니다."));
        if(feed.getId() != user.getId()){
            new IllegalAccessException("잘못된 접근입니다.");
        }

        feed.update(content);
        hashTagRepository.deleteAllByFeedId(feedId);
        for (String tag : tags) {
            HashTag hashTag = HashTag.builder().tagname(tag).feedId(feedId).build();
            hashTagRepository.save(hashTag);
        }
    }

    public void delete(Long feedId, User user) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시물입니다."));
        if(feed.getId() != user.getId()){
            new IllegalAccessException("잘못된 접근입니다");
        }
        //해시태그
        hashTagRepository.deleteAllByFeedId(feedId);
        //댓글
        commentRepository.deleteAllByFeedId(feedId);
        //좋아요
        feedGoodRepository.deleteAllByFeedId(feedId);
        //피드 이미지
        //S3 이미지 삭제
        List<FeedImage> feedImages = feedImageRepository.findAllByFeedId(feedId);
        feedImages.stream().forEach(feedImage ->
            {
                int idx = feedImage.getFeedImgUrl().indexOf("static");
                String url = feedImage.getFeedImgUrl().substring(idx);
                s3Uploader.delete(url);
            }
        );
        feedImageRepository.deleteAllByFeedId(feedId);
        //게시물
        feedRepository.deleteById(feedId);
    }

    public void post(FeedPostRequest request, List<MultipartFile> image, User user) {
        Feed feed = Feed.builder()
                .content(request.getContent())
                .user(user)
                .build();

        feedRepository.save(feed);

        Arrays.stream(request.getTag().split(" "))
                .forEach(hash ->
            hashTagRepository.save(HashTag.builder().tagname(hash).feedId(feed.getId()).build())
        );

        image.stream().forEach(file -> {
            try {
                String url = s3Uploader.upload(file, "static");
                FeedImage feedImage = FeedImage.builder().feedImgUrl(url).feed(feed).build();
                feedImageRepository.save(feedImage);
            } catch (IOException e) {
                log.info("error", e);
                throw new RuntimeException("업로드 실패");
            }
        });
    }
}
