package com.jcq.license.create;

import com.jcq.license.create.Exception.LicenseCreateException;
import com.jcq.license.create.manager.CustomLicenseManager;
import com.jcq.license.create.param.CustomKeyStoreParam;
import com.jcq.license.create.param.LicenseCreatorParam;
import de.schlichtherle.license.*;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 *
 * <p><b>使用truelicense，生成license，创建证书功能后面可以单独抽取一个服务，不必集成在项目中</b></p>
 *
 * <p>参考链接：</p>
 * &ensp;&ensp;&ensp;<a href="https://blog.csdn.net/LiuCJ_20000/article/details/140291573">参考博客</a> <br>
 *
 * <p>与此链接不同的点：</p>
 * &ensp;&ensp;&ensp;1. 生成公私钥命令，添加参数-keysize 1024 密码等命令 <br>
 *
 * <p>可以使用单元测试类 {com.jcq.license.create.LicenseCreateTest#main(String[])} 来创建证书</p>
 *
 * @author : jucunqi
 * @since  2025/3/12
 */
public class LicenseCreator {

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");
    private final LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * 生成License证书
     * @return boolean
     */
    public boolean generateLicense(){
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();

            licenseManager.store(licenseContent,new File(param.getLicensePath()));

            return true;
        }catch (Exception e){
            throw new LicenseCreateException(MessageFormat.format("证书生成失败：{0}", param), e);
        }
    }

    /**
     * 初始化证书生成参数
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(){
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                ,param.getPrivateKeysStorePath()
                ,param.getPrivateAlias()
                ,param.getStorePass()
                ,param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,privateStoreParam
                ,cipherParam);
    }

    /**
     * 设置证书生成正文信息
     * @return de.schlichtherle.license.LicenseContent
     */
    private LicenseContent initLicenseContent(){
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验服务器硬件信息
        licenseContent.setExtra(param.getLicenseCheckModel());

        return licenseContent;
    }
}
