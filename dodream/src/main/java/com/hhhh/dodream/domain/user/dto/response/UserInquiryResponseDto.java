package com.hhhh.dodream.domain.user.dto.response;

import com.hhhh.dodream.domain.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInquiryResponseDto {
    private String nickname;
    private String loginId;
    private String address;
    private String email;
    private String profileImg;

    public static UserInquiryResponseDto from(UserEntity user) {
        return UserInquiryResponseDto.builder()
                .nickname(user.getNickname())
                .loginId(user.getLoginId())
                .address(user.getAddress())
                .email(user.getEmail())
                .profileImg(user.getImagePath())
                .build();
    }
}
