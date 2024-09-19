package com.hhhh.dodream.global.exception.kind;

public class ExpiredTokenException extends CustomeException {
    public ExpiredTokenException(String message) {
        super(401, message);
    }
}
