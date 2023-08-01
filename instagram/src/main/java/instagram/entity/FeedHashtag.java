package instagram.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FeedHashtag {

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
