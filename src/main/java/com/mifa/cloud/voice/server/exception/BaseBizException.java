package com.mifa.cloud.voice.server.exception;

/**
 * @author songxm
 */
public class BaseBizException extends RuntimeException {
    private static final long serialVersionUID = -7180599528683421531L;

    /**
     * 消息代码
     */
    protected String code;
    /**
     * 详细消息
     */
    protected String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    /**
     * 构造一个 BaseBizException
     */
    public BaseBizException() {
        super();
    }

    /**
     * 构造一个带指定详细消息的BaseRuntimeException
     *
     * @param message 消息代码
     */
    public BaseBizException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 构造一个带指定详细消息的BaseRuntimeException
     *
     * @param code    消息代码
     * @param message 详细消息
     */
    public BaseBizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造一个带代码、指定详细消息、原因的BaseRuntimeException
     *
     * @param code    消息代码
     * @param message 详细消息
     * @param cause   原因
     */
    public BaseBizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造一个指定详细消息、原因的BaseRuntimeException
     *
     * @param message 详细消息
     * @param cause   原因
     */
    public BaseBizException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

}
