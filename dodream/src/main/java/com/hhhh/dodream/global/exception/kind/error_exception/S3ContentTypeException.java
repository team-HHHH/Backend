package com.hhhh.dodream.global.exception.kind.error_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class S3ContentTypeException extends CustomException {
    public S3ContentTypeException(String message) {
        super(404, message);
    }
}
