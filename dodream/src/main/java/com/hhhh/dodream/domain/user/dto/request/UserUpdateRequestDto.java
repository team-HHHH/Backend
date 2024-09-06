package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;
    private String password;
    private String address;
}
