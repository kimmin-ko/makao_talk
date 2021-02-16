package com.mins.makao.common.exception.business;


import com.mins.makao.common.exception.ErrorCode;

public class DuplicateAccountException extends BusinessException {
    private static final long serialVersionUID = 8616719156161611507L;

    public DuplicateAccountException(String message) {
        super(message, ErrorCode.DUPLICATE_ACCOUNT);
    }

}
