package instagram.entity.feed;

import instagram.entity.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FeedGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;
}
