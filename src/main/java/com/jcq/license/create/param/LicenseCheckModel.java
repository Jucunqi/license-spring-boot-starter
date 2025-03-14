package com.jcq.license.create.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义需要校验的参数
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Data
public class LicenseCheckModel implements Serializable {

    private static final long serialVersionUID = -2314678441082223148L;

    /**
     * 可被允许IP地址白名单
     * */
    private List<String>  ipAddress;

    /**
     * 可被允许的MAC地址白名单（网络设备接口的物理地址，通常固化在网卡（Network Interface Card，NIC）的EEPROM（电可擦可编程只读存储器）中，具有全球唯一性。）
     * */
    private  List<String> macAddress;

    /**
     * 可允许的CPU序列号
     * */
    private String cpuSerial;

    /**
     * 可允许的主板序列号(硬件序列化？)
     * */
    private String mainBoardSerial;

}
