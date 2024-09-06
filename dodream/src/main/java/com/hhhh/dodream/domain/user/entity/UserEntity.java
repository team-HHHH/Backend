package com.hhhh.dodream.domain.user.entity;

import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "profile_img")
    private String imagePath;

    public UserInquiryResponseDto toInquiryDto() {
        return UserInquiryResponseDto.builder()
                .email(this.email)
                .loginId(this.loginId)
                .nickname(this.nickname)
                .address(this.address)
                .profileImg(this.imagePath)
                .build();
    }

    public void updateEntity(UserUpdateRequestDto updateRequestDto){
        String nickname = updateRequestDto.getNickname();
        String password = updateRequestDto.getPassword();
        String address = updateRequestDto.getAddress();
        if(nickname!=null){
            this.nickname=nickname;
        }
        if(password!=null){
            this.password =password;
        }
        if(address!=null){
            this.address=address;
        }
    }
}
