package com.hhhh.dodream.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public void encodePassword(String encodedPassword) {
        password = encodedPassword;
    }
}
