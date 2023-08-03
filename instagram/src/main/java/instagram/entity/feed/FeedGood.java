package instagram.entity.feed;

import instagram.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FeedGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public FeedGood(Feed feed, User user) {
        this.feed = feed;
        this.user = user;
    }
}
