package com.hhhh.dodream.global.exception.kind;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException(String message) {
        super(401, message);
    }
}
