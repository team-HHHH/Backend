package com.hhhh.dodream.domain.user.controller;

import com.hhhh.dodream.domain.user.dto.request.*;
import com.hhhh.dodream.domain.user.dto.response.UserDuplicatedResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserEmailCodeCheckResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import com.hhhh.dodream.domain.user.service.UserService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.security.custom.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/check/id")
    public ResponseDto checkIdDuplication(@RequestBody LoginIdDuplicatedCheckRequestDto requestDto) {
        UserDuplicatedResponseDto body = userService.checkLoginIdDuplication(requestDto.getLoginId());
        return BodyResponseDto.onSuccess("아이디 중복체크 성공", body);
    }

    @PostMapping("/check/nickname")
    public ResponseDto checkNicknameDuplication(@RequestBody NickNameDuplicatedCheckRequestDto requestDto) {
        UserDuplicatedResponseDto body = userService.checkNicknameDuplication(requestDto.getNickName());
        return BodyResponseDto.onSuccess("닉네임 중복체크 성공", body);
    }

    @PostMapping("/check/email")
    public ResponseDto checkEmailDuplication(@RequestBody EmailCheckRequestDto emailDto) {
        userService.sendAuthCode(emailDto.getEmail());
        return ResponseDto.onSuccess("이메일 인증코드 발송 성공");
    }

    @PostMapping("/check/emailcode")
    public ResponseDto checkEmailCode(@RequestBody EmailAuthRequestDto requestDto) {
        UserEmailCodeCheckResponseDto userEmailCodeCheckResponseDto = userService.checkAuthCode(requestDto);
        return BodyResponseDto.onSuccess("이메일 코드 인증 성공", userEmailCodeCheckResponseDto);
    }

    @PostMapping("/register")
    public ResponseDto registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto, HttpServletResponse response) {
        userService.register(userRegisterRequestDto, response);
        return ResponseDto.onSuccess("회원가입 성공");
    }

    @PutMapping("/register-detailed")
    public ResponseDto registerDetail(@ModelAttribute UserRegisterDetailRequestDto detailRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.registerDetail(detailRequestDto, userDetails.getUserId());
        return ResponseDto.onSuccess("회원 정보 등록 성공");
    }

    @GetMapping
    public ResponseDto getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInquiryResponseDto inquiryResponseDto = userService.get(userDetails.getUserId());

        return BodyResponseDto.onSuccess("정보 조회 성공", inquiryResponseDto);
    }

    @PatchMapping
    public ResponseDto updateUser(
            @Valid @RequestBody UserUpdateRequestDto updateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.update(updateRequestDto, userDetails.getUserId());

        return ResponseDto.onSuccess("정보 수정 성공");
    }

    @PatchMapping("/password")
    public ResponseDto updateUserPassword(
            @Valid @RequestBody UserPasswordUpdateRequestDto updateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.update(updateRequestDto, userDetails.getUserId());

        return ResponseDto.onSuccess("비밀번호 수정 성공");
    }

    @GetMapping("/nickname/status")
    public ResponseDto checkNickname(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.checkNickname(userDetails.getUserId());

        return ResponseDto.onSuccess("닉네임 존재");
    }
}
