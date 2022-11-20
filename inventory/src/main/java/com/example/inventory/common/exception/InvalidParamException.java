package com.example.inventory.common.exception;


import static com.example.inventory.common.exception.ErrorCode.COMMON_INVALID_PARAMETER;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super(COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParamException(String errorMsg) {
        super(errorMsg, COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
