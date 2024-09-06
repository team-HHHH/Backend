package com.hhhh.dodream.domain.user.service;

import com.hhhh.dodream.global.security.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final Long ACCESS_TOKEN_EXPIRED_TTL = 600000L;
    private final Long REFRESH_TOKEN_EXPIRED_TTL = 86400000L;

    public void reissue(HttpServletResponse response, String refreshToken) {
        if (refreshToken == null) {
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

        String newAccessToken = jwtUtil.createToken(
                "access", userId, role, ACCESS_TOKEN_EXPIRED_TTL);
        String newRefreshToken = jwtUtil.createToken(
                "refresh", userId, role, REFRESH_TOKEN_EXPIRED_TTL);
        response.setHeader("Authorization", "Bearer "+newAccessToken);
        response.setHeader("Refresh", refreshToken);
    }
}
