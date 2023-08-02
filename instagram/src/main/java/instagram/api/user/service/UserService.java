package instagram.api.user.service;

import instagram.api.user.dto.request.LoginRequestDto;
import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.dto.response.LoginResponse;
import instagram.config.auth.LoginUser;
import instagram.config.jwt.JwtUtils;
import instagram.entity.user.User;
import instagram.entity.user.UserEnum;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static instagram.entity.user.UserEnum.USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("존재하는 이메일 입니다.");
        }
        
        User user = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .username(request.getUsername())
                .role(USER)
                .build();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequestDto request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()); // 인증정보
        Authentication authentication = authenticationManager.authenticate(authRequest); // 인증
        SecurityContextHolder.getContext().setAuthentication(authentication); // 인증정보를 SecurityContextHolder 넣는다.
        // accessToken 만들기
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);
        return new LoginResponse(loginUser, accessToken);
    }
}
