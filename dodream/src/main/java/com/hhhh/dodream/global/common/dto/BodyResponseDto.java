package com.hhhh.dodream.global.common.dto;

import lombok.Getter;

@Getter
public class BodyResponseDto<T> extends ResponseDto {
    T body;

    public BodyResponseDto(ResultDto result, T body) {
        super(result);
        this.body = body;
    }

    public static <T> BodyResponseDto<T> of(int code, String message, T body){
        ResultDto result = new ResultDto(code, message);
        return new BodyResponseDto<>(result, body);
    }

    public static <T> BodyResponseDto<T> onSuccess(String message, T body){
        ResultDto result = new ResultDto(200, message);
        return new BodyResponseDto<>(result, body);
    }
}
