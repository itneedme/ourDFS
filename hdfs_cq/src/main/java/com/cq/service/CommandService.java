package com.cq.service;

import com.cq.util.CommunityUtil;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandService {

    private static Logger logger = LoggerFactory.getLogger(CommandService.class);

    private static String command;

    /*@Value("${nmap.command}")
    public void setCommand(String command){
        CommandService.command = command;
    }*/

    public void getCommand(String command) {
        try {
            //获取当前系统的环境
            Runtime rt = Runtime.getRuntime();
            //执行
            Process p = null;
            p = rt.exec(command);
            //获取执行后的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String msg = null;
            //输出
            while((msg = br.readLine()) != null){
                System.out.println(msg);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void execCommand(String[] cmd) {
        Process p;
        try {
            // 执行命令
            p = Runtime.getRuntime().exec(cmd);
            // 取得命令结果的输出流
            InputStream fis = p.getInputStream();
            // 用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            //直到读完为止
            while((line = br.readLine()) != null) {
                System.out.println("line: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> getResult(String ip){
        // 创建空集合存筛选的结果
        List<Map<String,Object>> portList = new ArrayList<>();
        String returnData = getReturnData(ip);
        try{
            if(!StringUtils.isEmpty(returnData)){
                // 解析String类型的xml
                Document xmldoc = DocumentHelper.parseText(returnData);
                //得到根节点
                Element rootEl = xmldoc.getRootElement();
                if(rootEl != null) {
                    Element addrEl = rootEl.element("host").element("address");
                    if(addrEl != null) {
                        //扫描到的ip
                        String scanIp = addrEl.attributeValue("addr");
                        if(scanIp.equals(ip)){
                            logger.debug("ip匹配成功，开始解析...");
                            //ip类型
                            String ipType = StringUtils.isEmpty(addrEl.attributeValue("addrtype"))?"":"地址:"+addrEl.attributeValue("addrtype")+" ";
                            //获取所有子节点的跟节点，xpath
                            List<Element> ports = rootEl.selectNodes("//ports/port");
                            if(ports.size()>0){
                                for(Element el:ports){
                                    Map<String,Object> portMap = new HashMap<>();
                                    //协议
                                    String agreement = el.attributeValue("protocol");
                                    //端口号
                                    String port = el.attributeValue("portid");

                                    Element stateEl = el.element("state");
                                    //状态
                                    String state = StringUtils.isEmpty(stateEl.attributeValue("state"))?"":"状态:"+stateEl.attributeValue("state")+" ";
                                    //reason
                                    String reason = StringUtils.isEmpty(stateEl.attributeValue("reason"))?"":"reason:"+stateEl.attributeValue("reason")+" ";
                                    //reason_ttl
                                    String reasonTTL = StringUtils.isEmpty(stateEl.attributeValue("reason_ttl"))?"":"reason_ttl:"+stateEl.attributeValue("reason_ttl")+" ";

                                    Element serviceEl = el.element("service");
                                    //服务名
                                    String servicename = StringUtils.isEmpty(serviceEl.attributeValue("name"))?"":"服务名:"+serviceEl.attributeValue("name")+" ";
                                    //product
                                    String product = StringUtils.isEmpty(serviceEl.attributeValue("product"))?"":"product:"+serviceEl.attributeValue("product")+" ";
                                    //操作系统
                                    String ostype = StringUtils.isEmpty(serviceEl.attributeValue("ostype"))?"":"操作系统:"+serviceEl.attributeValue("ostype")+" ";
                                    //主机名
                                    String hostname = StringUtils.isEmpty(serviceEl.attributeValue("hostname"))?"":"主机名:"+serviceEl.attributeValue("hostname")+" ";
                                    //附加信息
                                    String extrainfo = StringUtils.isEmpty(serviceEl.attributeValue("extrainfo"))?"":"附加信息:"+serviceEl.attributeValue("extrainfo")+" ";
                                    portMap.put("id", CommunityUtil.generateUUID());
                                    portMap.put("ip",ip);
                                    portMap.put("wcode","12");
                                    portMap.put("website","Nmap扫描工具");
                                    portMap.put("port",port);
                                    portMap.put("agreement",agreement);
                                    String other = hostname + ostype + state + ipType + servicename + extrainfo + product + reason + reasonTTL;
                                    portMap.put("other",other);
                                    portList.add(portMap);
                                }
                                logger.debug("解析数据成功...");
                            }
                        }
                    }

                }else {
                    logger.debug("解析数据为空...");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("解析nmap数据失败...");
        }
        return portList;
    }

    /**
     * 调用nmap进行扫描
     * @return 执行回显
     * */
    public String getReturnData(String ip) {
        Process process = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("D:/Nmap/nping.exe " + "--tcp-connect -p 8000 129.211.209.15");
            logger.debug("开始对{}进行端口扫描...", ip);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
            String line = null;
            while((line = reader.readLine()) != null){
                stringBuffer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}


