package instagram.entity.comment;

import instagram.entity.user.User;
import instagram.entity.feed.Feed;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
