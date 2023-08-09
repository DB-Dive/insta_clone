package instagram.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequestDto {

    private String id;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @NotBlank
    private String nickname;

    public SignupRequestDto(String id, String password, String username, String nickname) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}
