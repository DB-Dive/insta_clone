package instagram.api.user.service;

import instagram.api.user.dto.FeedData;
import instagram.api.user.dto.UserData;
import instagram.api.user.dto.UserDto;
import instagram.api.user.dto.request.LoginRequestDto;
import instagram.api.user.dto.request.ProfileEditRequestDto;
import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.dto.response.FollowerResponse;
import instagram.api.user.dto.response.FollowingResponse;
import instagram.api.user.dto.response.LoginResponse;
import instagram.api.user.dto.response.ProfileResponse;
import instagram.common.GlobalException;
import instagram.config.auth.LoginUser;
import instagram.config.jwt.JwtUtils;
import instagram.config.s3.S3Uploader;
import instagram.entity.user.Follow;
import instagram.entity.user.User;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.FollowRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static instagram.entity.user.UserEnum.USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EntityManager em;
    private final FeedRepository feedRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmailOrPhoneNumber(request.getId(), request.getId())) {
            throw new GlobalException("존재하는 계정 입니다.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new GlobalException("존재하는 이름 입니다.");
        }

        String id = request.getId();
        String email = "";
        String phoneNumber = "";
        if (id.contains("@")) {
            email = id;
        } else if (id.matches("[0-9]{10,}")) {
            phoneNumber = id;
        } else {
            throw new GlobalException("이메일 또는 전화번호 형식이어야 합니다.");
        }

        User user = User.builder()
                .email(email)
                .phoneNumber(phoneNumber)
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .username(request.getUsername())
                .role(USER)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequestDto request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()); // 인증정보
        Authentication authentication = authenticationManager.authenticate(authRequest); // 인증
        SecurityContextHolder.getContext().setAuthentication(authentication); // 인증정보를 SecurityContextHolder 넣는다.
        // accessToken 만들기
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);
        return new LoginResponse(loginUser, accessToken);
    }

    @Transactional
    public void follow(Long toUserId, User me) {
        User toUser = userRepository.findById(toUserId).orElseThrow(
                () -> new GlobalException("존재하지 않는 회원입니다.")
        );
        Follow follow = new Follow(me, toUser);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long toUserId, User me) {
        User toUser = userRepository.findById(toUserId).orElseThrow(
                () -> new GlobalException("존재하지 않는 회원입니다.")
        );
        followRepository.deleteByToUserAndFromUser(toUser, me);
    }

    public FollowingResponse getFollowings(String username, Pageable pageable) {
        PageImpl<UserData> followingUsers = userRepository.findFollowingUsersByUsername(username, pageable);
        return new FollowingResponse(followingUsers);
    }

    public FollowerResponse getFollowers(String username, Pageable pageable) {

        // toUser의 타입이 User이기 때문에 f.toUser로 받을 수 있다.
        TypedQuery<User> result = em.createQuery("select f.fromUser from Follow f where f.toUser.username=:username", User.class);
        result.setParameter("username", username);

        result.setFirstResult((int) pageable.getOffset());
        result.setMaxResults(pageable.getPageSize());

        List<User> userList = result.getResultList();
        List<UserDto> userDtoList = userDtoListMapping(userList);
        Long count = em.createQuery("select count(f) from Follow f where f.toUser.username=:username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        PageImpl<UserDto> userDtos = new PageImpl<>(userDtoList, pageable, count);
        int totalPages = userDtos.getTotalPages();
        int currentPage = userDtos.getNumber();
        return new FollowerResponse(userDtoList, totalPages, currentPage);

    }

    public ProfileResponse getProfile(String username, Pageable pageable) {
        if (!userRepository.existsByUsername(username)) {
            throw new GlobalException("존재하지 않는 계정입니다.");
        }
        ProfileResponse response = userRepository.findByUsernameProfile(username);
        PageImpl<FeedData> feeds = feedRepository.findFeedInfo(username, pageable);
        response.setFeeds(feeds.getContent());
        response.setTotalPage(feeds.getTotalPages());
        response.setCurrentPage(feeds.getNumber());
        return response;

    }

    @Transactional
    public void editProfile(LoginUser loginUser, ProfileEditRequestDto profileEditRequestDto, MultipartFile multipartFile) {

        try {
            User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow();

            String localDate = LocalDate.now().toString();
            String uploadImage = s3Uploader.upload(multipartFile, localDate);

            user.setUsername(profileEditRequestDto.getUsername());
            user.setNickname(profileEditRequestDto.getNickname());
            user.setProfileImgUrl(uploadImage);
            user.setDescription(profileEditRequestDto.getDescription());

            userRepository.save(user);
        } catch (Exception e) {
            // 예외 처리 코드 추가
            e.printStackTrace();
            log.info("error", e);
        }
    }

    private List<UserDto> userDtoListMapping(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUserProfileImage(user.getProfileImgUrl());
            userDto.setUsername(user.getUsername());
            userDto.setNickname(user.getNickname());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

}
