package instagram.api.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import instagram.api.user.dto.response.LoginResponse;
import instagram.config.auth.LoginUser;
import instagram.config.jwt.JwtUtils;
import instagram.entity.user.User;
import instagram.entity.user.UserEnum;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static instagram.config.jwt.JwtProperties.HEADER_STRING;
import static instagram.config.jwt.JwtProperties.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class KakoUserService {
    private final JwtUtils jwtUtils;

    @Value("${social.kakao.clientId}")
    private String KAKAO_CLIENT_ID;
    @Value("${social.kakao.redirectUri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${social.kakao.clientSecret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${social.kakao.tokenUri}")
    private String KAKAO_TOKEN_URI;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;

    public LoginResponse kakaoLogin(String code) throws JsonProcessingException {
        String kakaoAccessToken = getAccessToken(code);
        System.out.println("accessToken = " + kakaoAccessToken);

        JsonNode kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        LoginUser loginUser = getLoginUser(kakaoUserInfo);

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);
        return new LoginResponse(loginUser, accessToken);
    }


    private String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Response Body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); //카카오 공식문서 기준 authorization_code 로 고정
        params.add("client_id", KAKAO_CLIENT_ID); // 카카오 Dev 앱 REST API 키
        params.add("redirect_uri", KAKAO_REDIRECT_URI); // 카카오 Dev redirect uri
        params.add("code", code); // 프론트에서 인가 코드 요청시 받은 인가 코드값
        params.add("client_secret", KAKAO_CLIENT_SECRET); // 카카오 Dev 카카오 로그인 Client Secret

        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 카카오로부터 Access token 받아오기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                KAKAO_TOKEN_URI, // "https://kauth.kakao.com/oauth/token"
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return objectMapper.readTree(accessTokenResponse.getBody()).get("access_token").asText();
    }

    private static JsonNode getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_STRING, TOKEN_PREFIX + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );


        return objectMapper.readTree(response.getBody());
    }

    private LoginUser getLoginUser(JsonNode kakaoUserInfo) {
        String providerId = kakaoUserInfo.get("id").asText();
        JsonNode kakao_account = kakaoUserInfo.get("kakao_account");
        String nickname = kakao_account.get("profile").get("nickname").asText();
        String email = kakao_account.get("email").asText();
        String username = "K_" + providerId + "_" + nickname;
        String phoneNumber = "";

        User kakaoUser = userRepository.findByEmailOrUsernameOrPhoneNumber(email, username, phoneNumber).orElse(null);
        if (kakaoUser == null) {
            kakaoUser = User.builder()
                    .email(email)
                    .password(UUID.randomUUID().toString())
                    .phoneNumber(phoneNumber)
                    .username(username)
                    .nickname(nickname)
                    .role(UserEnum.USER)
                    .provider("Kakao")
                    .providerId(providerId)
                    .build();
            userRepository.save(kakaoUser);
        }

        LoginUser loginUser = new LoginUser(kakaoUser);
        return loginUser;
    }
}
