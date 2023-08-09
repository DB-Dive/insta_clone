package instagram.config.auth;

import instagram.entity.user.User;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsernameOrPhoneNumber(input, input, input).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id: " + input)
        );
        return new LoginUser(user);
    }
}
