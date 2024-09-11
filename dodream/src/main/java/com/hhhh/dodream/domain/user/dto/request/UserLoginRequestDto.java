package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String loginId;
    private String password;
}
