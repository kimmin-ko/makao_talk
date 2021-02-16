package com.mins.makao.common.exception.business;


import com.mins.makao.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -8588675970692740602L;

    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorcode) {
        super(message);
        this.errorCode = errorcode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
