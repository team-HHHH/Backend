package com.hhhh.dodream.domain.auth.controller;

import com.hhhh.dodream.domain.auth.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.auth.dto.response.UserLoginResponseDto;
import com.hhhh.dodream.domain.auth.service.AuthService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseDto reissueTokens(HttpServletResponse response, @RequestHeader("Refresh") String refreshToken) {
        authService.reissue(response, refreshToken);

        return ResponseDto.onSuccess("새 jwt 토큰 발급 성공");
    }

    @PostMapping("/login/custom")
    public ResponseDto customLogin(HttpServletResponse response, @RequestBody UserLoginRequestDto loginRequestDto) {
        UserLoginResponseDto loginResponseDto = authService.customLogin(response, loginRequestDto);

        return BodyResponseDto.onSuccess("로그인 성공", loginResponseDto);
    }

    @PostMapping("/login/oauth")
    public ResponseDto oauthLogin(HttpServletResponse response, @RequestBody UserLoginRequestDto loginRequestDto) {
        UserLoginResponseDto loginResponseDto = authService.oauthLogin(response, loginRequestDto);

        return BodyResponseDto.onSuccess("로그인 성공", loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseDto logout(HttpServletResponse response, @RequestHeader("Authorization") String accessToken) {
        authService.logout(response, accessToken);

        return ResponseDto.onSuccess("로그아웃 성공");
    }
}
