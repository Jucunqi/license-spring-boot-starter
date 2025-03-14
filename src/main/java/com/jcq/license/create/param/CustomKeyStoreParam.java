package com.jcq.license.create.param;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 自定义参数 KeyStoreParam，目的：重写getStream方法从指定位置读取密钥
 *
 * @author : jucunqi
 * @since : 2025/3/12
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    /**
     * 密钥路径
     */
    private final String storePath;
    /**
     * 别名
     */
    private final String alias;
    /**
     * 密码
     */
    private final String storePwd;
    /**
     * 密码
     */
    private final String keyPwd;



    public CustomKeyStoreParam(Class aClass, String storePath, String alias, String storePwd, String keyPwd) {
        super(aClass, storePath);
        this.storePath = storePath;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * AbstractKeyStoreParam中默认实现是读取项目中的，所以进行重写
     * @return InputStream 输入流
     * @throws IOException io异常
     */
    @Override
    public InputStream getStream() throws IOException {
        File file = new File(storePath);
        if (file.exists()) {
            return Files.newInputStream(file.toPath());
        } else {
            throw new FileNotFoundException(storePath);
        }
    }
}
