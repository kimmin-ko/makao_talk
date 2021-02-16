package com.mins.makao.common.exception.business;


import com.mins.makao.common.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {
    private static final long serialVersionUID = 2231976757290594633L;

    public DuplicateEmailException(String message) {
        super(message, ErrorCode.DUPLICATE_EMAIL);
    }

}
