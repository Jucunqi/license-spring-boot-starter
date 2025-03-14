package com.jcq.license.verify;

import com.jcq.license.create.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Linux服务器相关机器码获取实现类
 * @author : jucunqi
 * @since : 2025/3/12
 */
@Slf4j
public class LinuxServerInfos extends AbstractServerInfos{
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所用网络接口
        List<InetAddress>  inetAddresses = getLocalAllInetAddress();
        if (inetAddresses != null && inetAddresses.size()>0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String :: toLowerCase).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;
        //1.获取所用网络接口
        List<InetAddress> inetAddresses  = getLocalAllInetAddress();
        if (inetAddresses  != null && inetAddresses .size()>0){
            //2.获取所用网络接口的Mac地址
            // result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
            List<String> list = new ArrayList<>();
            Set<String> uniqueValues = new HashSet<>();
            for (InetAddress inetAddress : inetAddresses) {
                String macByInetAddress = getMacByInetAddress(inetAddress);
                if (uniqueValues.add(macByInetAddress)) {
                    list.add(macByInetAddress);
                }
            }
            result = list;
            return result;
        }
        return result;
    }

    @Override
    protected String getCpuSerial() throws Exception {
        try {
            //序列号
            String serialNumber = null;

            //使用dmidecode命令获取CPU序列号
            String[] shell =  {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
            Process process = Runtime.getRuntime().exec(shell);
            process.getOutputStream().close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = reader.readLine().trim();
            if (StringUtils.isNotBlank(line)){
                serialNumber = line;
            }
            reader.close();
            return serialNumber;
        } catch (Exception e) {
            log.error("license认证cpu序列号失败，原因：{}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected String MainBoardSerial() throws Exception {
        try {
            //序列号
            String serialNumber = null;

            //使用dmidecode命令获取主板序列号
            String[] shell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
            Process process = Runtime.getRuntime().exec(shell);
            process.getOutputStream().close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = reader.readLine().trim();
            if(StringUtils.isNotBlank(line)){
                serialNumber = line;
            }

            reader.close();
            return serialNumber;
        } catch (Exception e) {
            log.error("license获取服务器主板序列号异常，原因: {}", e.getMessage(), e);
        }
        return null;
    }

}

