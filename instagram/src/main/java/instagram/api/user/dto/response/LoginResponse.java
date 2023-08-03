package instagram.api.user.dto.response;

import instagram.config.auth.LoginUser;
import lombok.Getter;

@Getter
public class LoginResponse {
    private Long userId;
    private String email;
    private String username;
    private String accessToken;

    public LoginResponse(LoginUser loginUser, String accessToken) {
        this.userId = loginUser.getUser().getId();
        this.email = loginUser.getUser().getEmail();
        this.username = loginUser.getUser().getUsername();
        this.accessToken = accessToken;
    }
}
