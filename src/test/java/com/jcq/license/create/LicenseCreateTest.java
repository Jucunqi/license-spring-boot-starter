package com.jcq.license.create;

import com.jcq.license.create.param.LicenseCheckModel;
import com.jcq.license.create.param.LicenseCreatorParam;
import com.jcq.license.create.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试创建证书<br>
 * 创建证书前先在命令行执行以下三步：<br>
 * 1、首先要用KeyTool工具来生成私匙库：（-alias别名 –validity 3650表示10年有效）<br>
 *  <pre>keytool -genkey -alias privatekey -keystore privateKeys.store -storepass "123456q" -keypass "123456q" -keysize 1024 -validity 3650</pre> <br>
 *
 * 2、然后把私匙库内的公匙导出到一个文件当中：<br>
 * <pre>keytool -export -alias privatekey -file certfile.cer -keystore privateKeys.store -storepass "123456q" </pre> <br>
 *
 * 3、然后再把这个证书文件导入到公匙库：
 * <pre>keytool -import -alias publiccert -file certfile.cer -keystore publicCerts.store -storepass "123456q" </pre>
 *
 */
public class LicenseCreateTest {
    public static void main(String[] args){

        // 创建生成证书的参数
        /*
        {
            “subject”: “license_demo”,
            “privateAlias”: “privateKey”,
            “keyPass”: “private_password1234”,
            “storePass”: “public_password1234”,
            “licensePath”: “E:/LicenseDemo/license.lic”,
            “privateKeysStorePath”: “E:/LicenseDemo/privateKeys.keystore”,
            “issuedTime”: “2022-04-26 14:48:12”,
            “expiryTime”: “2022-08-22 00:00:00”,
            “consumerType”: “User”,
            “consumerAmount”: 1,
            “description”: “这是证书描述信息”,
            “licenseCheckModel”: {
            “ipAddress”: [
                "192.168.3.57"
            ],
            “macAddress”: [
                "b6:01:fa:0a:0d:f5"
            ],
            “cpuSerial”: "",
            “mainBoardSerial”: ""
            }
         }

         */
        LicenseCreatorParam param = new LicenseCreatorParam();
        param.setSubject("ocr");
        param.setPrivateAlias("privatekey");
        param.setKeyPass("your key pass");
        param.setStorePass("your store pass");
        param.setLicensePath("your license path");
        param.setPrivateKeysStorePath("your keys store path");
        param.setIssuedTime(DateUtil.parseDate("2025-03-12")); // 起始时间
        param.setExpiryTime(DateUtil.parseDate("2025-05-13")); // 截止时间
        param.setConsumerType("user");
        param.setConsumerAmount(1);
        param.setDescription("这是证书描述信息");
        LicenseCheckModel checkModel = new LicenseCheckModel();
        List<String> macAddressList = new ArrayList<>();
        // macAddressList.add("6c:92:cf:0a:53:c0");
        // checkModel.setMacAddress(macAddressList);
        param.setLicenseCheckModel(checkModel);
        // 创建证书
        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();
        System.out.println("证书生成结果：" + result);
    }
}