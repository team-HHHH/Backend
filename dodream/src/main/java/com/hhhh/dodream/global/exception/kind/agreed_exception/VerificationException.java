package com.hhhh.dodream.global.exception.kind.agreed_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class VerificationException extends CustomException {

    public VerificationException(String message) {
        super(202, message);
    }
}
