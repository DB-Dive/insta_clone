package instagram.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import instagram.common.dto.ErrorResponse;
import instagram.config.auth.LoginUser;
import instagram.entity.user.User;
import instagram.entity.user.UserEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

import static instagram.config.jwt.JwtProperties.*;

@Component
@Slf4j
public class JwtUtils {
    private final Key key;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JwtUtils(@Value("${jwt.app.jwtSecretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessTokenFromLoginUser(LoginUser loginUser) {
        return Jwts.builder()
                .claim("id", loginUser.getUser().getId())
                .claim("email", loginUser.getUser().getEmail())
                .claim("username", loginUser.getUser().getUsername())
                .claim("role", loginUser.getUser().getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String parseJwtToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING); // Bearer 포함되어 있음
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    public boolean validationJwtToken(String token, HttpServletResponse response) throws IOException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //토큰에 대한 검증 로직
            return true;
        } catch (ExpiredJwtException e) {
            setResponse(response, e, "토큰시간이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            setResponse(response, e, "JWT 토큰이 유효하지 않습니다.");
        } catch (UnsupportedJwtException e) {
            setResponse(response, e, "지원하지 않는 토큰입니다.");
        }
        return false;
    }

    private void setResponse(HttpServletResponse response, Exception e, String errorMessage) throws IOException {
        log.error(errorMessage, e);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(errorMessage)));
    }

    public LoginUser getLoginUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);
        User user = User.builder()
                .id(id)
                .email(email)
                .username(username)
                .role(UserEnum.valueOf(role))
                .build();
        return new LoginUser(user);
    }
}
