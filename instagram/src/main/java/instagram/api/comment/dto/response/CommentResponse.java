package instagram.api.comment.dto.response;

import instagram.entity.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    String content;

    public CommentResponse(Comment comment) {
        this.content = comment.getContent();
    }
}
