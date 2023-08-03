package instagram.api.comment.controller;

import instagram.api.comment.dto.request.CommentRequest;
import instagram.api.comment.dto.response.CommentResponse;
import instagram.api.comment.service.CommentService;
import instagram.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{feedId}")
    public CommentResponse createComment(@PathVariable Long feedId, @RequestBody CommentRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        return commentService.createComment(feedId, request, loginUser);
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        return commentService.updateComment(commentId, request, loginUser);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal LoginUser loginUser) {
        return commentService.deleteComment(commentId, loginUser);
    }
}
