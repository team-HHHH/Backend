package com.hhhh.dodream.domain.user.controller;

import com.hhhh.dodream.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import com.hhhh.dodream.domain.user.service.UserService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

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
}
