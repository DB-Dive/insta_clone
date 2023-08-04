package instagram.entity.feed;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FeedHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "HASHTAG_ID")
    private HashTag hashtag;
}
