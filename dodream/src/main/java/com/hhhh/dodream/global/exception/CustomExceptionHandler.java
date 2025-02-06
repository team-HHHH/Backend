package com.hhhh.dodream.global.exception;

import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.exception.kind.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

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
}
