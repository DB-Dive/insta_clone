package instagram.entity.feed;

import instagram.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_ID")
    private Feed feed;


    @Builder
    public FeedGood(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedGood)) return false;
        FeedGood feedGood = (FeedGood) o;
        return o != null && Objects.equals(getId(), feedGood.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
