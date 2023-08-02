package instagram.api.user.controller;

import instagram.api.user.dto.request.LoginRequestDto;
import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.dto.response.LoginResponse;
import instagram.api.user.service.UserService;
import instagram.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequestDto request) {
        return userService.login(request);
    }

    @GetMapping("/logout")
    public void logout(@AuthenticationPrincipal LoginUser loginUser) {
        log.info(loginUser.getUser().getEmail() + " 로그아웃 요청");
    }

    @PostMapping("/follow/{userId}")
    public void follow(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        userService.follow(userId, loginUser.getUser().getId());
    }

    @DeleteMapping("/unfollow/{userId}")
    public void unfollow(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        userService.unfollow(userId, loginUser.getUser().getId());
    }

}


