package com.mifa.cloud.voice.server.exception;

/**
 * @author songxm
 */
public class BusinessException extends BaseRuntimeException {
    private static final long serialVersionUID = 5848203255667327423L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
