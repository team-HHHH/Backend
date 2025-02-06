package com.hhhh.dodream.domain.auth.service;

import com.hhhh.dodream.domain.auth.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.auth.dto.response.UserLoginResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.common.enums.KeyPrefixEnum;
import com.hhhh.dodream.global.common.service.RedisService;
import com.hhhh.dodream.global.exception.kind.agreed_exception.VerificationException;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import com.hhhh.dodream.global.exception.kind.error_exception.ExpiredTokenException;
import com.hhhh.dodream.global.exception.kind.error_exception.InvalidTokenException;
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
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void reissue(HttpServletResponse response, String refreshToken) {
        validateRefreshToken(refreshToken);

        Long userId = jwtUtil.getUserId(refreshToken);

        validateRefreshTokenWithRedis(refreshToken, userId);

        String role = jwtUtil.getRole(refreshToken);

        this.setJWT(
                response,
                userId,
                role
        );
    }

    public UserLoginResponseDto customLogin(HttpServletResponse response, UserLoginRequestDto loginRequestDto) {
        UserEntity user = userRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new DataFoundException("user db에 없는 데이터입니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new VerificationException("로그인에 실패했습니다.");
        }

        this.setJWT(
                response,
                user.getId(),
                user.getRole()
        );

        return this.checkFirstLogin(user);
    }

    @Transactional
    public UserLoginResponseDto oauthLogin(HttpServletResponse response, UserLoginRequestDto loginRequestDto) {
        UserEntity user = findOrCreateUser(loginRequestDto);

        this.setJWT(
                response,
                user.getId(),
                user.getRole()
        );

        return this.checkFirstLogin(user);
    }

    public void logout(HttpServletResponse response, String accessToken) {
        SecurityContextHolder.clearContext();
        response.setHeader("Authorization", null);
        response.setHeader("Refresh", null);

        Long userId = jwtUtil.getUserId(accessToken);

        redisService.setKeyWithExpiration(
                KeyPrefixEnum.LOGOUT_ACCESS.getKeyPrefix() + userId,
                accessToken,
                jwtUtil.getExpirationInMillis(accessToken)
        );
        redisService.deleteKey(KeyPrefixEnum.REFRESH.getKeyPrefix() + userId);
    }

    private void validateRefreshToken(String refreshToken) {
        if (refreshToken == null) {
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
    }

    private void validateRefreshTokenWithRedis(String refreshToken, Long userId) {
        String refreshTokenInRedis = redisService.getValue(KeyPrefixEnum.REFRESH.getKeyPrefix() + userId);

        if (!refreshTokenInRedis.equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 리프레쉬 토큰임");
        }
    }

    private UserLoginResponseDto checkFirstLogin(UserEntity user) {
        if (ObjectUtils.isEmpty(user.getNickname())) {
            return UserLoginResponseDto.from(true);
        } else {
            return UserLoginResponseDto.from(false);
        }
    }

    private void setJWT(
            HttpServletResponse response,
            Long userId,
            String role
    ) {
        long accessTokenTTL = 600000L;
        String newAccessToken = jwtUtil.createToken(
                "access",
                userId,
                role,
                accessTokenTTL
        );

        long refreshTokenTTL = 86400000L;
        String newRefreshToken = jwtUtil.createToken(
                "refresh",
                userId,
                role,
                refreshTokenTTL
        );

        redisService.setKeyWithExpiration(
                KeyPrefixEnum.REFRESH.getKeyPrefix() + userId,
                newRefreshToken,
                refreshTokenTTL
        );

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Refresh", newRefreshToken);
    }

    private UserEntity findOrCreateUser(UserLoginRequestDto loginRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findByLoginId(loginRequestDto.getLoginId());

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            loginRequestDto.setPassword(passwordEncoder.encode(loginRequestDto.getPassword()));
            UserEntity user = UserEntity.from(loginRequestDto);
            userRepository.save(user);

            return user;
        }
    }
}
