package com.jcq.license.verify.aop;


import com.jcq.license.annotataion.RequireLicense;
import com.jcq.license.autoconfigure.LicenseConfigProperties;
import com.jcq.license.create.Exception.LicenseInterceptException;
import com.jcq.license.verify.LicenseVerify;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <p>License拦截</p>
 * <p>拦截 {@link com.jcq.license.annotataion.RequireLicense} 注解</p>
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Slf4j
@Aspect
@Component
public class RequireLicenseAspect {

    private final LicenseVerify licenseVerify;
    private final LicenseConfigProperties properties;

    /**
     * 构造注入
     * @param licenseVerify License校验类
     */
    public RequireLicenseAspect(LicenseVerify licenseVerify, LicenseConfigProperties properties) {
        this.licenseVerify = licenseVerify;
        this.properties = properties;
    }

    @Around("@annotation(requireLicense)")
    public Object switchSource(ProceedingJoinPoint point, RequireLicense requireLicense) throws Throwable {

        if (!requireLicense.value()) {
            return point.proceed();
        }
        if (!properties.getEnableLicense()) {
            log.info("接口：{}  ,不执行license验证", point.getSignature().getName());
            return point.proceed();
        }
        boolean verify = licenseVerify.verify();
        log.info("接口：{} , License验证结果：{}", point.getSignature().getName(), verify);
        if (verify) {
            return point.proceed();
        }
        throw new LicenseInterceptException("License认证失败");
    }
}
