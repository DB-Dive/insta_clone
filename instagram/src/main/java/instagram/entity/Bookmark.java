package instagram.entity;

import instagram.user.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "FEED_ID")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;



}
