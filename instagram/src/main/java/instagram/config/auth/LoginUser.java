package instagram.config.auth;

import instagram.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginUser implements UserDetails {
    private final User user;

    //권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + user.getRole());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { //계정의 만료 여부 리턴
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정의 잠김 여부 리턴
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호 만료 여부 리턴
        return true;
    }

    @Override
    public boolean isEnabled() { //계정의 활성화 여부 리턴
        return true;
    }
}
