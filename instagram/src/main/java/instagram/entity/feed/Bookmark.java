package instagram.entity.feed;

import instagram.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor // TODO: 유니크 제약 조건 실패시 처리 어케함?
//@Table(uniqueConstraints = {@UniqueConstraint( name = "FEED_USER_UNIQUE",
//        columnNames = {"FEED_ID", "USER_ID"} )})
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Bookmark(Feed feed, User user) {
        this.createdAt = LocalDateTime.now();
        this.feed = feed;
        this.user = user;
    }
}
