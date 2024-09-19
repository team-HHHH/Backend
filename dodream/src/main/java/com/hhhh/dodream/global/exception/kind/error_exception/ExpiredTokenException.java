package com.hhhh.dodream.global.exception.kind.error_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException(String message) {
        super(401, message);
    }
}
