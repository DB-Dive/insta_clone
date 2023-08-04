package instagram.entity.comment;

import instagram.entity.feed.Feed;
import instagram.entity.user.User;
import instagram.entity.feed.Feed;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
    public Comment(String content, Feed feed, User user) {
        this.content = content;
        this.feed = feed;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
    public void updateComment(String content) {
        this.content = content;
    }

    @Builder
    public Comment(String content, LocalDateTime modifiedAt, Feed feed, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = modifiedAt;
        this.feed = feed;
        this.user = user;
    }
}
