package com.hhhh.dodream.domain.user.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    private String nickname;
    private String address;
    private String email;
    private String imagePath;
}
