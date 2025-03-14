package com.jcq.license.verify.param;

import lombok.Data;


/**
 * license证书校验参数类
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Data
public class LicenseVerifyParam {

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;

}
