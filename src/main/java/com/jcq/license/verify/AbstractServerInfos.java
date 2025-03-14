package com.jcq.license.verify;

import com.jcq.license.create.param.LicenseCheckModel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取用户 服务器硬件信息（机器码）:为LicenseCheckModel服务提供硬件信息 （用于获取客户服务器的基本信息，如：IP、Mac地址、CPU序列号、主板序列号等）
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Slf4j
public abstract class AbstractServerInfos {

    /**
     * Description: 组装需要额外校验的License参数
     *
     * @return   com.changzhouwater.ocrimagerecognize.license.create.param.LicenseCheckModel
    */
    public LicenseCheckModel getServerInfos(){
        LicenseCheckModel licenseCheckModel = new LicenseCheckModel();

        try {
            licenseCheckModel.setIpAddress(this.getIpAddress());
            licenseCheckModel.setMacAddress(this.getMacAddress());
            // 本项目中没有使用到cpu 和 主板信息作为校验
            // licenseCheckModel.setCpuSerial(this.getCpuSerial());
            // licenseCheckModel.setMainBoardSerial(this.MainBoardSerial());
        }catch (Exception e){
            log.error("获取服务器硬件信息失败", e);
        }
        return licenseCheckModel;
    }

    /**
     * Description:  获取IP地址信息
     *
     * @return java.util.List<java.lang.String>
    */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * Description: 获取Mac地址（网络设备接口的物理地址，通常固化在网卡（Network Interface Card，NIC）的EEPROM（电可擦可编程只读存储器）中，具有全球唯一性。）
     *
     * @return java.util.List<java.lang.String>
    */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * Description: 获取CPU序列号
     *
     * @return java.util.List<java.lang.String>
    */
    protected abstract String getCpuSerial() throws Exception;

    /**
     * Description: 获取主板序列号
     *
     * @return java.lang.String
    */
    protected abstract String MainBoardSerial() throws Exception;

    /**
     * Description: 获取当亲啊服务器上所用符合条件的InetAddress
     *
     * @return java.util.List<java.net.InetAddress>
    */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception{
        List<InetAddress> result  = new ArrayList<>();

        //遍历所用网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ){
            NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
            // 在所用接口下再遍历IP地址
            for(Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements();){
                InetAddress inetAddr= (InetAddress) inetAddresses.nextElement();

                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }



    /**
     * Description: 获取某个网络接口的Mac地址
     *
     * @return java.lang.String
    */
    protected String getMacByInetAddress(InetAddress inetAddress) throws Exception{
        try {

            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            if (mac == null) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < mac.length; i++) {
                if (i != 0){
                    stringBuilder.append("-");
                }

                //将十六进制byte转化为字符串
                String hexString = Integer.toHexString(mac[i] & 0xFF);
                if(hexString.length() == 1){
                    stringBuilder.append("0" + hexString);
                }else {
                    stringBuilder.append(hexString);
                }
            }

            return stringBuilder.toString().toUpperCase();
        }catch (Exception e){
            log.error("获取某个网络接口的Mac地址异常，inetAddress: {}", e.getMessage(), e);
        }
        return null;
    }
}

