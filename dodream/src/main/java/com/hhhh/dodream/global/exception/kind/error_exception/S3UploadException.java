package com.hhhh.dodream.global.exception.kind.error_exception;

import com.hhhh.dodream.global.exception.kind.CustomException;

/**
 * S3 업로드 예외
 */
public class S3UploadException extends CustomException {
    public S3UploadException(String message) {
        super(403, message);
    }
}
