package com.hhhh.dodream.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultDto {
    private int resultCode;
    private String resultMessage;

    public static ResultDto success(String resultMessage) {
        return new ResultDto(200, resultMessage);
    }
}
