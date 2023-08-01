package instagram.user.controller;

import instagram.user.dto.request.SignupRequestDto;
import instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }

}
