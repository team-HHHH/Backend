package com.hhhh.dodream.global.common.dto;

import lombok.*;

@Getter
@Builder
public class PreSignedUrlResponseDto {
    private String preSingedUrl;
    private String imageSaveUrl;

    public static PreSignedUrlResponseDto of(String preSingedUrl, String imageSaveUrl){
        return PreSignedUrlResponseDto.builder()
                .preSingedUrl(preSingedUrl)
                .imageSaveUrl(imageSaveUrl)
                .build();
    }
}
