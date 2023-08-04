package instagram.entity.feed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @Builder
    public FeedImage(String feedImgUrl, Feed feed) {
        this.feedImgUrl = feedImgUrl;
        this.feed = feed;
    }
}
