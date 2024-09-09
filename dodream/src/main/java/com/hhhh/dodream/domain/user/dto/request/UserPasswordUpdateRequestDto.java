package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordUpdateRequestDto {
    private String originpw;
    private String newpw;
}
