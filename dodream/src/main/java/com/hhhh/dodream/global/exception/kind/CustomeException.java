package com.hhhh.dodream.global.exception.kind;

import lombok.Getter;

@Getter
public class CustomeException extends RuntimeException {
    private final int code;

    public CustomeException(int code, String message) {
        super(message);
        this.code = code;
    }
}
