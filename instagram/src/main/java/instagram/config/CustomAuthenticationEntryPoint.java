package instagram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import instagram.common.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String loginException = authException.getClass().getSimpleName();
        if (loginException.equals(UsernameNotFoundException.class.getSimpleName())) {
            setResponse(response, "존재하지 않는 이메일입니다.");
        } else if (loginException.equals(BadCredentialsException.class.getSimpleName())) {
            setResponse(response, "비밀번호가 틀렸습니다.");
        }
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
