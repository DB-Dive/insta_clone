package instagram.api.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import instagram.api.user.dto.request.LoginRequestDto;
import instagram.api.user.dto.request.ProfileEditRequestDto;
import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.dto.response.FollowerResponse;
import instagram.api.user.dto.response.FollowingResponse;
import instagram.api.user.dto.response.LoginResponse;
import instagram.api.user.dto.response.ProfileResponse;
import instagram.api.user.service.KakoUserService;
import instagram.api.user.service.UserService;
import instagram.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final KakoUserService kakaoUserService;

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
        userService.follow(userId, loginUser.getUser());
    }

    @DeleteMapping("/unfollow/{userId}")
    public void unfollow(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        userService.unfollow(userId, loginUser.getUser());
    }

    @GetMapping("/{username}/followings")
    public FollowingResponse getFollowings(@PathVariable String username, Pageable pageable) {
        return userService.getFollowings(username, pageable);
    }

    @GetMapping("/{username}/followers")
    public FollowerResponse getFollowers(@PathVariable String username, Pageable pageable) {
        return userService.getFollowers(username, pageable);
    }

    @GetMapping("/{username}")
    public ProfileResponse getProfile(@PathVariable String username, Pageable pageable) {
        return userService.getProfile(username, pageable);
    }

    @PutMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public void editProfile(@AuthenticationPrincipal LoginUser loginUser,
                            @RequestPart(value = "profileEditRequestDto") ProfileEditRequestDto profileEditRequestDto,
                            @RequestParam(value = "userProfileImage") MultipartFile multipartFile) {

        userService.editProfile(loginUser, profileEditRequestDto, multipartFile);
    }

    @GetMapping("/login/oauth2/callback/kakao")
    public LoginResponse kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code);
    }
}


