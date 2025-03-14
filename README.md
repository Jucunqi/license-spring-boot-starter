# 自定义starter，用于项目添加license认证
## 使用说明
### 1、创建证书
参考单元测试类 com.jcq.license.create.LicenseCreateTest

### 2、使用者引入本依赖
1. 添加pom依赖

```xml

<dependency>
    <groupId>com.jcq.yylicense</groupId>
    <artifactId>yylicense-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```
2. 添加application.yml 或者 application.properties配置
对应配置会有idea提示
```yml
# 是否启用license验证
license.enable-license=true
# 证书主题
license.subject=ocr
# 证书别名
license.public-alias=publicCert
# 证书密码，与创建的时候保持一致
license.store-pass=your pass
# 证书路径，确保路径真实存在
license.license-path=your license path
# 证书公钥路径
license.public-keys-store-path=your keys path
```
3. 如果设置`license.enable-license=true`,那么项目启动时就会校验证书

4. 同时本服务还提供了一个注解以及对应的aop拦截方法

    1. 提供注解 `com.jcq.license.annotataion.RequireLicense`

    2. 拦截逻辑说明
   > 该注解用可于方法上，被拦截的方法在执行前会进行证书校验逻辑，如果校验失败，则会抛出`LicenseInterceptException`异常，用户可根据条件配置对应异常处理器，然后处理成服务统一的响应格式，用于响应请求
    
5. 添加依赖完成，配置完成后，启动服务正常情况下会出现如下日志
```txt
2025-03-13 09:40:59.327  INFO 66695 --- [           main] com.jcq.license.verify.LicenseVerify  : 服务启动，检查是否启用license验证，结果：true
2025-03-13 09:40:59.396  INFO 66695 --- [           main] com.jcq.license.verify.LicenseVerify  : 证书安装成功，证书有效期：2025-03-12 00:00:00 - 2025-05-13 00:00:00
```