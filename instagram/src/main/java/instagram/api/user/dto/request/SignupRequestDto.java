package instagram.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class SignupRequestDto {

    @Email
    private String email;
    private String password;
    private String username;
    private String nickname;

    public SignupRequestDto(String email, String password, String username, String nickname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}
