package com.hhhh.dodream.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateRequestDto {
    @NotBlank
    private String originPw;

    @NotBlank
    private String newPw;
}
