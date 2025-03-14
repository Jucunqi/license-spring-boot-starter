package com.jcq.license.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 证书认证属性类
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Data
@ConfigurationProperties(prefix = "license")
public class LicenseConfigProperties {

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

    /**
     * 是否启用license认证
     */
    private Boolean enableLicense;
}
