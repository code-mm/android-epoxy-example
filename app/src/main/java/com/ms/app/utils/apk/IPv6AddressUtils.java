package com.ms.app.utils.apk;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IPv6AddressUtils {

    private static final String TAG = "IPV6"; // TAG标签

    private static final String getIpv6Cmd = "/system/bin/ip -6 addr show "; // 默认获取Ipv6地址的shell命令
    private static final int IPV6LEN_LEFT_BIT = 6; // 默认，截取的Ipv6字符串左起始位
    private static final int IPV6LEN_RIGHT_BIT = 1; // 默认，截取的Ipv6字符串右结束位

    private static int ipv6AddrNum = 0; // 记录Ipv6地址的个数
    private static String ipv6AddrString = ""; // 由Ipv6地址组成的个数

    
    
    private static IPv6AddressUtils iPv6AddressUtils = new IPv6AddressUtils();

    public static IPv6AddressUtils getInstance() {
        return iPv6AddressUtils;
    }
    
    /**
     * 构造函数：GetIPv6AddressUtils()
     * 参数：    无
     * 注意：    调用此构造函数将获取所有网口的Ipv6地址
     */
    public IPv6AddressUtils() {
        Log.d(TAG, "get all interface ipv6 address.");
        getIpv6Addr(getIpv6Cmd);
    }

    /**
     * 构造函数：GetIPv6AddressUtils(String iface)
     * 参数：    网口的名称，例如：eth0、wlan0等
     * 注意：    调用此构造函数将获取传入网口参数的Ipv6地址
     */
    public IPv6AddressUtils(String iface) {
        Log.d(TAG, "get " + iface + " ipv6 address.");
        getIpv6Addr(getIpv6Cmd + iface);
    }

    /**
     * 方法名：getIpv6AddrNumber()
     * 参数：  无
     * 功能：  获取Ipv6地址字符串
     * 返回值：返回由Ipv6地址组成的字符串，例如：::1/128&fdd1:850a:264c::581/64&fe80::86ff:4cff:fe4b:ecb1/64&
     */
    public String getIpv6AddrString() {
        return ipv6AddrString;
    }

    /**
     * 方法名：getIpv6AddrNumber()
     * 参数：  无
     * 功能：  获取Ipv6地址个数
     * 返回值：返回getIpv6Addr()方法获取的Ipv6地址个数
     */
    public int getIpv6AddrNumber() {
        return ipv6AddrNum;
    }

    /**
     * 方法名：getIpv6Addr(String command)
     * 参数：  shell下需要执行的命令，例如： /system/bin/ip -6 addr show
     * 功能：  1、获取Ipv6地址，并统计地址的个数
     * 返回值：无
     */
    private void getIpv6Addr(String command) {
        String ipv6addrTemp = "";
        ipv6AddrNum = 0;
        ipv6AddrString = "";

        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if ((line.contains("inet6")) && (line.contains("scope"))) {
                    ipv6addrTemp = line.substring(line.indexOf("inet6") + IPV6LEN_LEFT_BIT, line.lastIndexOf("scope") - IPV6LEN_RIGHT_BIT);
                    ipv6AddrString = ipv6AddrString + ipv6addrTemp + "&";
                    ipv6AddrNum++;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ipv6AddrString = "";
            Log.d(TAG, "get ip error" + e);
        }
    }
}
