package com.hhhh.dodream.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private boolean isFirstLogin;

    public static UserLoginResponseDto from(boolean isFirstLogin){
        return new UserLoginResponseDto(isFirstLogin);
    }
}
