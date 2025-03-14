package com.jcq.license.create.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * License证书生成类需要的参数
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Data
public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = 2832129012982731724L;

    /**
     * 证书subject
     * */
    private String subject;

    /**
     * 密钥级别
     * */
    private String privateAlias;

    /**
     * 密钥密码（需要妥善保存，密钥不能让使用者知道）
    */
    private String keyPass;

    /**
     * 访问密钥库的密码
     * */
    private String storePass;

    /**
     * 证书生成路径
     * */
    private String licensePath;

    /**
     * 密钥库存储路径
     * */
    private String privateKeysStorePath;

    /**
     * 证书生效时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();


    /**
     * 证书的失效时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * 用户的使用类型
     * */
    private String consumerType ="user";

    /**
     * 用户使用数量
     * */
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     * */
    private String description = "";

    /**
     * 额外的服务器硬件校验信息（机器码）
     * */
    private LicenseCheckModel licenseCheckModel;
}
