package com.hhhh.dodream.global.exception.kind.agreed_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class DuplicatedException extends CustomException {

    public DuplicatedException(String message) {
        super(201, message);
    }
}
