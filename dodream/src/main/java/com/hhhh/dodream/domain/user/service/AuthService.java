package com.hhhh.dodream.domain.user.service;

import com.hhhh.dodream.domain.user.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserLoginResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.common.enums.RedisKeyPrefixEnum;
import com.hhhh.dodream.global.common.service.RedisService;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import com.hhhh.dodream.global.exception.kind.error_exception.ExpiredTokenException;
import com.hhhh.dodream.global.exception.kind.error_exception.InvalidTokenException;
import com.hhhh.dodream.global.exception.kind.agreed_exception.VerificationException;
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
            throw new InvalidTokenException("리프레쉬 토큰이 없음");
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("리프레쉬 토큰 만료됨");
        }

        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new InvalidTokenException("리프레쉬 토큰 카테고리 값이 유효하지 않음");
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        this.setJWT(userId, role, response);
    }

    public UserLoginResponseDto customLogin(HttpServletResponse response,
                                            UserLoginRequestDto loginRequestDto) {
        String loginId = loginRequestDto.getLoginId();
        UserEntity user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new DataFoundException("user db에 없는 데이터입니다."));
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return this.checkFirstLogin(user, response);
        }
        throw new VerificationException("로그인에 실패했습니다.");
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
        } else {
            user = optionalUser.get();
        }
        return this.checkFirstLogin(user, response);
    }

    public void logout(HttpServletResponse response, String refreshToken) {
        SecurityContextHolder.clearContext();
        response.setHeader("Authorization", null);
        response.setHeader("Refresh", null);
        Long userId;
        try {
            userId = jwtUtil.getUserId(refreshToken);
        } catch (ExpiredJwtException e) {
            return;
        }
        redisService.deleteKey(RedisKeyPrefixEnum.REFRESH, userId);
    }

    public void setJWT(UserEntity user, HttpServletResponse response) {
        Long userId = user.getId();
        String role = user.getRole();
        this.setJWT(userId, role, response);
    }

    private void setJWT(Long userId, String role,
                        HttpServletResponse response) {
        String newAccessToken = jwtUtil.createToken(
                "access", userId, role, ACCESS_TOKEN_EXPIRED_TTL);
        String newRefreshToken = jwtUtil.createToken(
                "refresh", userId, role, REFRESH_TOKEN_EXPIRED_TTL);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Refresh", newRefreshToken);
        redisService.setKey(RedisKeyPrefixEnum.REFRESH, userId, newRefreshToken);
    }

    private UserLoginResponseDto checkFirstLogin(UserEntity user,
                                                 HttpServletResponse response) {
        this.setJWT(user, response);
        if (ObjectUtils.isEmpty(user.getNickname())) {
            return UserLoginResponseDto.from(true);
        } else {
            return UserLoginResponseDto.from(false);
        }
    }
}
