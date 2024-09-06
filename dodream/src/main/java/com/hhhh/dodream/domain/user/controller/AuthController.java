package com.hhhh.dodream.domain.user.controller;

import com.hhhh.dodream.domain.user.service.AuthService;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseDto reissueTokens(HttpServletResponse response,
                                     @RequestHeader("Refresh") String refreshToken) {
        authService.reissue(response, refreshToken);
        return ResponseDto.generalSuccess("새 jwt 토큰 발급 성공");
    }

}
