package com.hhhh.dodream.global.common.dto;

import lombok.Getter;

@Getter
public class BodyResponseDto<T> extends ResponseDto {
    T body;

    public BodyResponseDto(ResultDto result, T body) {
        super(result);
        this.body = body;
    }

    public static <T> BodyResponseDto<T> onSuccess(String message, T body) {
        ResultDto success = ResultDto.success(message);
        return new BodyResponseDto<>(success, body);
    }
}
