package com.hhhh.dodream.global.exception;

import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.exception.kind.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseDto handleCustomException(CustomException e) {
        log.warn("{} - {}", e.getCode(), e.getMessage());

        return ResponseDto.of(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseDto handleIOException(IOException e) {
        log.warn(e.getMessage(), e);

        return ResponseDto.of(500, "입출력 에러 발생");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseDto handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);

        return ResponseDto.of(501, "알 수 없는 런타임 예외 발생");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);

        return ResponseDto.of(502, "유효성 검사 예외 발생");
    }
}
