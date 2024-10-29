package com.hhhh.dodream.global.exception.kind.agreed_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class UnAuthorizedException extends CustomException {
    public UnAuthorizedException(String message) {
        super(204, message);
    }
}
