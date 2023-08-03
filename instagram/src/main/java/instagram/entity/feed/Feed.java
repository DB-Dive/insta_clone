package instagram.entity.feed;

import instagram.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;

//    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
//    List<FeedGood> feedGoods = new ArrayList<>();

    @Builder
    public Feed(User user, String content, LocalDateTime modifiedAt) {
        this.user = user;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}
