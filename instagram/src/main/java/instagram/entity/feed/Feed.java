package instagram.entity.feed;

import instagram.entity.comment.Comment;
import instagram.entity.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Feed(String content, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public void update(String updateContent){
        this.content = updateContent;
    }
}
