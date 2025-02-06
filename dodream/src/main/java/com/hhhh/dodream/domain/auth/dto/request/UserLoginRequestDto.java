package com.hhhh.dodream.domain.auth.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {
    private String loginId;
    private String password;

    public void encodePassword(String encodedPassword) {
        password = encodedPassword;
    }
}
