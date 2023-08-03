package instagram.api.user.service;

import instagram.api.user.dto.FeedData;
import instagram.api.user.dto.UserData;
import instagram.api.user.dto.UserDto;
import instagram.api.user.dto.request.LoginRequestDto;
import instagram.api.user.dto.request.SignupRequestDto;
import instagram.api.user.dto.response.FollowerResponse;
import instagram.api.user.dto.response.FollowingResponse;
import instagram.api.user.dto.response.LoginResponse;
import instagram.api.user.dto.response.ProfileResponse;
import instagram.config.auth.LoginUser;
import instagram.config.jwt.JwtUtils;
import instagram.entity.user.Follow;
import instagram.entity.user.User;
import instagram.repository.feed.FeedRepository;
import instagram.repository.user.FollowRepository;
import instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static instagram.entity.user.UserEnum.USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final EntityManager em;
    private final FeedRepository feedRepository;


    @Transactional
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

    @Transactional
    public LoginResponse login(LoginRequestDto request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()); // 인증정보
        Authentication authentication = authenticationManager.authenticate(authRequest); // 인증
        SecurityContextHolder.getContext().setAuthentication(authentication); // 인증정보를 SecurityContextHolder 넣는다.
        // accessToken 만들기
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessTokenFromLoginUser(loginUser);
        return new LoginResponse(loginUser, accessToken);
    }

    @Transactional
    public void follow(Long toUserId, Long fromUserId) {

        User toUser = userRepository.findById(toUserId).orElseThrow();//null일때 던져버리기
        User fromUser = userRepository.findById(fromUserId).orElseThrow();

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long toUserId, Long fromUserId) {

        User toUser = userRepository.findById(toUserId).orElseThrow();//null일때 던져버리기
        User fromUser = userRepository.findById(fromUserId).orElseThrow();
        followRepository.deleteByToUserAndFromUser(toUser, fromUser);
    }

    public FollowingResponse getFollowings(Long userId, Pageable pageable) {
        PageImpl<UserData> followingUsers = userRepository.findFollowingUsers(userId, pageable);
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
        System.out.println("count = " + count);
        PageImpl<UserDto> userDtos = new PageImpl<>(userDtoList, pageable,count);
        int totalPages = userDtos.getTotalPages();
        int currentPage = userDtos.getNumber();
        return new FollowerResponse(userDtoList, totalPages, currentPage);

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
    public ProfileResponse getProfile(String username, Pageable pageable) {
        ProfileResponse response = userRepository.findByUsernameProfile(username);
        PageImpl<FeedData> feeds =  feedRepository.findFeedInfo(username, pageable);
        response.setFeeds(feeds.getContent());
        response.setTotalPage(feeds.getTotalPages());
        response.setCurrentPage(feeds.getNumber());
        return response;

    }
}
