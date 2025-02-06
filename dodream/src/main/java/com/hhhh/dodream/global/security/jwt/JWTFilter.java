package com.hhhh.dodream.global.security.jwt;

import com.hhhh.dodream.global.common.enums.KeyPrefixEnum;
import com.hhhh.dodream.global.common.service.RedisService;
import com.hhhh.dodream.global.exception.kind.error_exception.ExpiredTokenException;
import com.hhhh.dodream.global.exception.kind.error_exception.InvalidTokenException;
import com.hhhh.dodream.global.security.custom.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    public JWTFilter(JWTUtil jwtUtil, RedisService redisService) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            return;
        }

        String accessToken = authorization.substring("Bearer ".length());

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("액세스 토큰 만료됨");
        }

        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            throw new InvalidTokenException("액세스 토큰 카테고리 값이 유효하지 않음");
        }

        Long userId = jwtUtil.getUserId(accessToken);

        String logoutAccessToken = redisService.getValue(KeyPrefixEnum.LOGOUT_ACCESS.getKeyPrefix() + userId);
        if (accessToken.equals(logoutAccessToken)) {
            throw new InvalidTokenException("로그아웃된 액세스 토큰입니다.");
        }

        String role = jwtUtil.getRole(accessToken);

        CustomUserDetails userDetails = new CustomUserDetails(userId, role);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
