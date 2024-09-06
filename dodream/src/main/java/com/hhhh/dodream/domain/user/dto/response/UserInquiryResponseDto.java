package com.hhhh.dodream.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInquiryResponseDto {
    private String nickname;
    private String loginId;
    private String address;
    private String email;
    private String profileImg;
}
