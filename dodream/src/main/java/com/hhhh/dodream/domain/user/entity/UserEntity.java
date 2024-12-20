package com.hhhh.dodream.domain.user.entity;

import com.hhhh.dodream.domain.user.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserRegisterDetailRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import static com.hhhh.dodream.global.common.utils.LambdaUtils.updateStringLambda;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "profile_img")
    private String imagePath;

    @Column(name = "role", nullable = false)
    private String role;

    public static UserEntity from(UserLoginRequestDto userLoginRequestDto) {
        return UserEntity.builder()
                .loginId(userLoginRequestDto.getLoginId())
                .password(userLoginRequestDto.getPassword())
                .role("ROLE_USER")
                .build();
    }

    public void modifyProfileImage(UserUpdateRequestDto updateRequestDto) {
        updateStringLambda(updateRequestDto.getNickname(), nickname -> this.nickname = nickname);
        updateStringLambda(updateRequestDto.getAddress(), address -> this.address = address);
        updateStringLambda(updateRequestDto.getEmail(), email -> this.email = email);
    }

    public void modifyProfileImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public void modifyPassword(String password) {
        this.password = password;
    }

    public void registerDetail(UserRegisterDetailRequestDto dto, String imagePath) {
        this.nickname = dto.getNickName();
        this.address = dto.getAddress();
        this.imagePath = imagePath;
    }

    public UserEntity(String email, String loginId, String password) {
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.role = "member";
    }
}
