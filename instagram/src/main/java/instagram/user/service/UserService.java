package instagram.user.service;

import instagram.user.dto.request.SignupRequestDto;
import instagram.user.entity.User;
import instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("존재하는 이메일 입니다.");
        }
        
        User user = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .username(request.getUsername())
                .build();
        userRepository.save(user);
    }
}
