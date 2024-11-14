package com.hhhh.dodream.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
    private ResultDto result;

    public static ResponseDto of(int code, String message) {
        ResultDto result = new ResultDto(code, message);
        return ResponseDto.builder().result(result).build();
    }

    public static ResponseDto onSuccess(String message) {
        ResultDto success = ResultDto.success(message);
        return ResponseDto.builder()
                .result(success)
                .build();
    }
}
