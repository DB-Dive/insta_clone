package instagram.api.user.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {
    @Email
    private String email;
    @NotBlank
    private String password;
}
