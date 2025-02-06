package com.hhhh.dodream.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @NotBlank
    private String nickname;

    @NotBlank
    private String address;

    @Email
    @NotNull
    private String email;

    private String imagePath;
}
