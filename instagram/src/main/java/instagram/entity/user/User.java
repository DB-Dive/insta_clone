package instagram.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String nickname;
    private String password;
    private String description;
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    private UserEnum role;
    private LocalDateTime createdAt;

    @Builder
    public User(Long id, String username, String nickname, String email, String password, UserEnum role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = "";
        this.profileImgUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F243FE450575F82662D";
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }


}
