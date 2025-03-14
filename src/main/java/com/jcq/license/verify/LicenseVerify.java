package com.jcq.license.verify;

import com.jcq.license.verify.manager.LicenseManagerHolder;
import com.jcq.license.verify.param.CustomKeyStoreParam;
import com.jcq.license.verify.param.LicenseVerifyParam;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;


/**
 * license证书校验类
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Slf4j
public class LicenseVerify {

    /**
     * 认证需要提供的参数
     */
    private final LicenseVerifyParam param;
    /**
     * 是否启用license
     */
    private final Boolean enableLicense;

    public LicenseVerify(LicenseVerifyParam param,Boolean enableLicense) {
        this.param = param;
        this.enableLicense = enableLicense;
    }

    /**
     * 安装License证书
     */
    public synchronized LicenseContent install(){

        log.info("服务启动，检查是否启用license验证，结果：" + enableLicense);
        if (!enableLicense) {
            return null;
        }
        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. 安装证书
        try{
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();

            result = licenseManager.install(new File(param.getLicensePath()));
            log.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",format.format(result.getNotBefore()),format.format(result.getNotAfter())));
        }catch (Exception e){
            log.error("证书安装失败！",e);
        }

        return result;
    }

    /**
     * 校验License证书
     * @return boolean
     */
    public boolean verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();

            log.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            return true;
        }catch (Exception e){
            log.error("证书校验失败！",e);
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     * @param param License校验类需要的参数
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }
}
