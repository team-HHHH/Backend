package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordUpdateRequestDto {
    private String originPw;
    private String newPw;
}
