package instagram.entity;

import instagram.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "FROM_USER_ID")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "TO_USER_ID")
    private User toUser;

}
