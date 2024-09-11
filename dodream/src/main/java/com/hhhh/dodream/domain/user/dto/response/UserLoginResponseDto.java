package com.hhhh.dodream.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private boolean firstLogin;

    public static UserLoginResponseDto from(boolean firstLogin){
        return new UserLoginResponseDto(firstLogin);
    }
}
