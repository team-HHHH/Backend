package com.hhhh.dodream.global.exception.kind;

import lombok.Getter;

@Getter
public class DuplicatedException extends CustomeException {
    public DuplicatedException(String message) {
        super(201, message);
    }
}
