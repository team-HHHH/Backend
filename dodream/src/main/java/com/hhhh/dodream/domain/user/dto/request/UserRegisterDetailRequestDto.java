package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDetailRequestDto {
    private String nickName;
    private String address;
    private String imagePath;
}
