package instagram.api.comment.service;

import instagram.api.comment.dto.request.CommentRequest;
import instagram.api.comment.dto.response.CommentResponse;
import instagram.config.auth.LoginUser;
import instagram.entity.comment.Comment;
import instagram.entity.feed.Feed;
import instagram.repository.comment.CommentRepository;
import instagram.repository.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public CommentResponse createComment(@PathVariable Long feedId, CommentRequest request, LoginUser loginUser) {

        // 코멘트 작성이기 때문에, feedId가 필요하다.
        // feed 레포에서 해당 feed를 찾아온다.
        Feed findedFeed = feedRepository.findById(feedId).orElseThrow();
        Comment comment = new Comment(request.getContent(), findedFeed, loginUser.getUser());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Transactional
    public CommentResponse updateComment(@PathVariable Long commentId, CommentRequest request, LoginUser loginUser) {

        Comment findedComment = commentRepository.findById(commentId).orElseThrow();

        Long loginUserId = loginUser.getUser().getId();
        Long commentUserId = findedComment.getUser().getId();

        if (Objects.equals(loginUserId, commentUserId)) {
            findedComment.updateComment(request.getContent());

        } else {
            throw new IllegalStateException("작성 본인만 수정 가능");
        }
        return new CommentResponse(findedComment);
    }
    @Transactional
    public String deleteComment(Long commentId, LoginUser loginUser) {

        Comment findedComment = commentRepository.findById(commentId).orElseThrow();

        Long loginUserId = loginUser.getUser().getId();
        Long commentUserId = findedComment.getUser().getId();

        if (Objects.equals(loginUserId, commentUserId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalStateException("작성 본인만 삭제 가능");
        }
        return "댓글이 삭제되었습니다.";
    }
}
