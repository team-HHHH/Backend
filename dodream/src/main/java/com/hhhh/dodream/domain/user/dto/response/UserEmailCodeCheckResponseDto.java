package com.hhhh.dodream.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEmailCodeCheckResponseDto {
    private boolean codeCorrect;
}
