package com.jcq.license.create.Exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Getter
public final class LicenseCreateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;
    /**
     * 异常对象
     */
    private Exception e;

    /**
     * 空构造方法，避免反序列化问题
     */
    public LicenseCreateException() {
    }

    public LicenseCreateException(String message) {
        this.message = message;
    }

    public LicenseCreateException(String message, Exception e) {
        this.message = message;
        this.e = e;
    }

    @Override
    public String getMessage() {
        return message;
    }

}