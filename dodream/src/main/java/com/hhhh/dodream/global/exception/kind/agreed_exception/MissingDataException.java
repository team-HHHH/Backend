package com.hhhh.dodream.global.exception.kind.agreed_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

public class MissingDataException extends CustomException {
    public MissingDataException(String message) {
        super(203, message);
    }
}
