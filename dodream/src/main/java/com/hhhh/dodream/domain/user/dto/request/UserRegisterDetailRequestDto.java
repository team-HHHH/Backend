package com.hhhh.dodream.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDetailRequestDto {
    private String nickName;
    private String address;
    private MultipartFile profileImage;
}
