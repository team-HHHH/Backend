package com.hhhh.dodream.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private ResultDto result;

    public static ResponseDto of(int code, String message) {
        ResultDto result = new ResultDto(code, message);
        return new ResponseDto(result);
    }

    public static ResponseDto onSuccess(String message) {
        ResultDto success = ResultDto.success(message);
        return new ResponseDto(success);
    }
}
