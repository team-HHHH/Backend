package com.hhhh.dodream.domain.user.entity;

import com.hhhh.dodream.domain.auth.dto.request.UserLoginRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserRegisterDetailRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import static com.hhhh.dodream.global.common.utils.LambdaUtils.updateStringFieldUsingLambda;

@Entity
@Getter
@Builder
@Table(name = "users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void modify(UserUpdateRequestDto updateRequestDto) {
        updateStringFieldUsingLambda(updateRequestDto.getNickname(), nickname -> this.nickname = nickname);
        updateStringFieldUsingLambda(updateRequestDto.getAddress(), address -> this.address = address);
        updateStringFieldUsingLambda(updateRequestDto.getEmail(), email -> this.email = email);
        updateStringFieldUsingLambda(updateRequestDto.getImagePath(), imagePath -> this.imagePath = imagePath);
    }

    public void modifyPassword(String password) {
        this.password = password;
    }

    public void registerDetail(UserRegisterDetailRequestDto dto) {
        this.nickname = dto.getNickName();
        this.address = dto.getAddress();
        this.imagePath = dto.getImagePath();
    }

    public UserEntity(String email, String loginId, String password) {
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.role = "member";
    }
}
