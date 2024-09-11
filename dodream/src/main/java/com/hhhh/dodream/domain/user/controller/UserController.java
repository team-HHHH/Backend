package com.hhhh.dodream.domain.user.controller;

import com.hhhh.dodream.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.request.*;
import com.hhhh.dodream.domain.user.dto.response.UserDuplicatedResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserEmailCodeCheckResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import com.hhhh.dodream.domain.user.service.AuthService;
import com.hhhh.dodream.domain.user.service.UserService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/check/id")
    public ResponseDto checkIdDuplication(@RequestBody LoginIdDuplicatedCheckRequestDto requestDto){
        UserDuplicatedResponseDto body = userService.checkLoginIdDuplication(requestDto.getLoginId());
        return BodyResponseDto.generalSuccess("아이디 중복체크 성공", body);
    }

    @PostMapping("/check/nickname")
    public ResponseDto checkNicknameDuplication(@RequestBody NickNameDuplicatedCheckRequestDto requestDto){
        UserDuplicatedResponseDto body = userService.checkNicknameDuplication(requestDto.getNickName());
        return BodyResponseDto.generalSuccess("닉네임 중복체크 성공", body);
    }

    @PostMapping("/check/email")
    public ResponseDto checkEmailDuplication(@RequestBody EmailCheckRequestDto emailDto){
        userService.sendAuthCode(emailDto.getEmail());
        return ResponseDto.generalSuccess("이메일 인증코드 발송 성공");
    }

    @PostMapping("/check/emailcode")
    public ResponseDto checkEmailCode(@RequestBody EmailAuthRequestDto requestDto){
        UserEmailCodeCheckResponseDto userEmailCodeCheckResponseDto = userService.checkAuthCode(requestDto);
        return BodyResponseDto.generalSuccess("이메일 코드 인증 성공", userEmailCodeCheckResponseDto);
    }

    @PostMapping("/register")
    public ResponseDto registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto, HttpServletResponse response){
        userService.register(userRegisterRequestDto,response);
        return ResponseDto.generalSuccess("회원가입 성공");
    }

    @PutMapping("/register-detailed")
    public ResponseDto registerDetail(@ModelAttribute UserRegisterDetailRequestDto detailRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        userService.registerDetail(detailRequestDto,userDetails.getUserId());
        return ResponseDto.generalSuccess("회원 정보 등록 성공");
    }

    @GetMapping
    public ResponseDto getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInquiryResponseDto body = userService.find(userDetails.getUserId());
        return BodyResponseDto.generalSuccess("정보 조회 성공", body);
    }

    @PatchMapping
    public ResponseDto updateUser(@RequestBody UserUpdateRequestDto updateRequestDto,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.update(updateRequestDto, userDetails.getUserId());
        return ResponseDto.generalSuccess("정보 수정 성공");
    }

    @PatchMapping("/changepw")
    public ResponseDto updateUserPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestBody UserPasswordUpdateRequestDto updateRequestDto) {
        userService.updatePassword(userDetails.getUserId(), updateRequestDto);
        return ResponseDto.generalSuccess("비밀번호 수정 성공");
    }

    @GetMapping("/check/nickname")
    public ResponseDto checkNickname(@AuthenticationPrincipal CustomUserDetails userDetails){
        userService.checkNickname(userDetails.getUserId());
        return ResponseDto.generalSuccess("닉네임 존재");
    }
}
