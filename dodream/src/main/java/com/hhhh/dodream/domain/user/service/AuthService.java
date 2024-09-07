package com.hhhh.dodream.domain.user.service;

import com.hhhh.dodream.domain.user.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserLoginResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.common.enums.RedisKeyPrefixEnum;
import com.hhhh.dodream.global.common.service.RedisService;
import com.hhhh.dodream.global.security.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisService redisService;

    private final Long ACCESS_TOKEN_EXPIRED_TTL = 600000L;
    private final Long REFRESH_TOKEN_EXPIRED_TTL = 86400000L;

    public void reissue(HttpServletResponse response, String refreshToken) {
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new RuntimeException("Refresh token null");
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Refresh token expired");
        }

        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new RuntimeException("Invalid token");
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        this.setJWTHeaders(userId, role, response);
    }

    public UserLoginResponseDto customLogin(HttpServletResponse response,
                                            UserLoginRequestDto loginRequestDto) {
        String loginId = loginRequestDto.getLoginId();
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("db에 없는 데이터입니다."));
        String EncodedPassword = passwordEncoder.encode(loginRequestDto.getPassword());
        if (user.getPassword().equals(EncodedPassword)) {
            return this.checkFirstLogin(user, response);
        }
        throw new RuntimeException("로그인에 실패했습니다.");
    }

    @Transactional
    public UserLoginResponseDto oauthLogin(HttpServletResponse response,
                                           UserLoginRequestDto loginRequestDto) {
        String loginId = loginRequestDto.getLoginId();
        String EncodedPassword = passwordEncoder.encode(loginRequestDto.getPassword());
        Optional<UserEntity> optionalUser = userRepository.findByLoginId(loginId);
        UserEntity user;
        if (optionalUser.isEmpty()) {
            user = UserEntity.builder()
                    .loginId(loginId)
                    .password(EncodedPassword)
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
            return UserLoginResponseDto.from(true);
        } else {
            user = optionalUser.get();
            return this.checkFirstLogin(user, response);
        }
    }

    public void logout(HttpServletResponse response, String refreshToken) {
        Long userId = jwtUtil.getUserId(refreshToken);
        redisService.deleteKey(RedisKeyPrefixEnum.REFRESH, userId);
        SecurityContextHolder.clearContext();
        response.setHeader("Authorization", null);
        response.setHeader("Refresh", null);
    }

    private void setJWTHeaders(UserEntity user, HttpServletResponse response) {
        Long userId = user.getId();
        String role = user.getRole();
        this.setJWTHeaders(userId, role, response);
    }

    private void setJWTHeaders(Long userId, String role,
                               HttpServletResponse response) {
        String newAccessToken = jwtUtil.createToken(
                "access", userId, role, ACCESS_TOKEN_EXPIRED_TTL);
        String newRefreshToken = jwtUtil.createToken(
                "refresh", userId, role, REFRESH_TOKEN_EXPIRED_TTL);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Refresh", newRefreshToken);
    }

    private UserLoginResponseDto checkFirstLogin(UserEntity user,
                                                 HttpServletResponse response) {
        if (ObjectUtils.isEmpty(user.getNickname())) {
            return UserLoginResponseDto.from(true);
        } else {
            this.setJWTHeaders(user, response);
            return UserLoginResponseDto.from(false);
        }
    }
}
