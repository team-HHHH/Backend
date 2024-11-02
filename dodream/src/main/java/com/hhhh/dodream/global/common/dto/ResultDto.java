package com.hhhh.dodream.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultDto {
    private int resultCode;
    private String resultMessage;

    public static ResultDto success(String resultMessage) {
        return ResultDto.builder()
                .resultCode(200)
                .resultMessage(resultMessage)
                .build();
    }
}
