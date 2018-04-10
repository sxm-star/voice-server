package com.mifa.cloud.voice.server.exception;

import lombok.Data;

/**
 * @author 宋烜明
 */
@Data
public class RetrofitException extends RuntimeException {
    private Integer code;
    private String message;

    public RetrofitException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof RetrofitException)) {
            return false;
        } else {
            RetrofitException other = (RetrofitException)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                Integer thisCode = this.getCode();
                Integer otherCode = other.getCode();
                if(thisCode == null) {
                    if(otherCode != null) {
                        return false;
                    }
                } else if(!thisCode.equals(otherCode)) {
                    return false;
                }

                String thisMessage = this.getMessage();
                String otherMessage = other.getMessage();
                if(thisMessage == null) {
                    if(otherMessage != null) {
                        return false;
                    }
                } else if(!thisMessage.equals(otherMessage)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RetrofitException;
    }
    @Override
    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        Integer code = this.getCode();
        int result1 = result * 59 + (code == null?0:code.hashCode());
        String message = this.getMessage();
        result1 = result1 * 59 + (message == null?0:message.hashCode());
        return result1;
    }
    @Override
    public String toString() {
        return "RetrofitException(code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }
}
