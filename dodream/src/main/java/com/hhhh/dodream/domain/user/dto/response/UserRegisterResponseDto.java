package com.hhhh.dodream.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterResponseDto {
    private String email;
    private String loginId;
    private String password;
}
