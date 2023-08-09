package instagram.api.user.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {
    private String id;
    @NotBlank
    private String password;
}
