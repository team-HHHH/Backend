package com.hhhh.dodream.domain.user.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateRequestDto {
    private String originPw;
    private String newPw;
}
