package instagram.entity.feed;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedImgUrl;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;
}
