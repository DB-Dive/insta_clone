package instagram.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String profileImgUrl;
    private String roles;
    private LocalDateTime createdAt;

    @Builder
    public User(String username, String nickname, String email, String password, String description) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = "";
        this.profileImgUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F243FE450575F82662D";
        this.roles = "USER";
        this.createdAt = LocalDateTime.now();
    }


}
