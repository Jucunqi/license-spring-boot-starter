package com.jcq.license.autoconfigure;

import com.jcq.license.verify.LicenseVerify;
import com.jcq.license.verify.param.LicenseVerifyParam;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * License配置类
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(LicenseConfigProperties.class)
public class LicenseAutoConfiguration {

    /**
     * 构造注入
     */
    private final LicenseConfigProperties licenseConfigProperties;

    @Bean(initMethod = "install")
    public LicenseVerify licenseVerify() {
        LicenseVerifyParam param = new LicenseVerifyParam();
        param.setSubject(licenseConfigProperties.getSubject());
        param.setPublicAlias(licenseConfigProperties.getPublicAlias());
        param.setStorePass(licenseConfigProperties.getStorePass());
        param.setLicensePath(licenseConfigProperties.getLicensePath());
        param.setPublicKeysStorePath(licenseConfigProperties.getPublicKeysStorePath());
        return new LicenseVerify(param, licenseConfigProperties.getEnableLicense());
    }
}