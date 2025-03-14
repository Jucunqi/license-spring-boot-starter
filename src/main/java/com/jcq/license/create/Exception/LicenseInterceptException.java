package com.jcq.license.create.Exception;

/**
 * 证书拦截逻辑异常
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
public class LicenseInterceptException extends RuntimeException {


    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public LicenseInterceptException() {
    }

    public LicenseInterceptException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
