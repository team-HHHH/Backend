package com.hhhh.dodream.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private boolean firstLogin;

    public static UserLoginResponseDto from(boolean firstLogin) {
        return UserLoginResponseDto.builder()
                .firstLogin(firstLogin)
                .build();
    }
}
