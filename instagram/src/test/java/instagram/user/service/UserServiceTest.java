package instagram.user.service;

import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.service.UserService;
import instagram.entity.user.User;
import instagram.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clear() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("존재하는 이메일 입니다.")
    void signup_fail() {
        //given
        String email = "yaejingo@gmail.com";
        User user = User.builder()
                .email(email)
                .build();
        userRepository.save(user);
        SignupRequestDto request = new SignupRequestDto(email, "1234", "yejin", "예진");

        //when //then
        Assertions.assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하는 이메일 입니다.");

    }

    @Test
    @DisplayName("회원가입 완료됐습니다.")
    void signup_success() {

        //given
        String email = "yaejingo@gmail.com";
        SignupRequestDto request = new SignupRequestDto(email, "1234", "yejin", "예진");

        //when
        userService.signup(request);

        // then
        Optional<User> findUser = userRepository.findByEmail(email);
        Assertions.assertThat(email).isEqualTo(findUser.get().getEmail());
    }
}
