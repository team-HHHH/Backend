package com.hhhh.dodream.global.exception.kind.error_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class DataFoundException extends CustomException {
    public DataFoundException(String message) {
        super(400, message);
    }
}
