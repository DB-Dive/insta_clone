package instagram.entity;

import instagram.user.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
