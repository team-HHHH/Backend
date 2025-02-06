package com.hhhh.dodream.global.exception.kind.error_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException(String message) {
        super(402, message);
    }
}
