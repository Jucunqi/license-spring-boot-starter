package com.jcq.license.verify;

import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Windows客户端相关机器码获取实现类
 * @author : jucunqi
 * @since : 2025/3/12
 */
public class WindowsServerInfos extends AbstractServerInfos{
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所用网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses!= null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;

        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

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
        //序列号
        String serialNumber  = "";

        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

    @Override
    protected String MainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }
}
