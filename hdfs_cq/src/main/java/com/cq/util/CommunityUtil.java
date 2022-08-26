package com.cq.util;

import com.cq.dao.DataNodeMapper;
import com.cq.entity.DataNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class CommunityUtil {

    @Autowired
    private DataNodeMapper dataNodeMapper;

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密, 针对字符串
    // MD5是32位长度的16进制字符串
    public static String md5(String key) {
        if(StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    // MD5加密, 针对文件
    public static String md5(File file) {
        String res = null;
        try {
            res = DigestUtils.md5DigestAsHex(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // 16进制转10进制
    public static int sixteenToTen(String hex) {
        String hex1 = hex.substring(0, 7);
        return (int) Integer.parseInt(hex1,16);
    }

    // 根据转成的10进制生成相应的余数，选择放到哪台服务器下
    public int selectServer1(int ten, int size) {
        int chose = ten % 6 + 1;
        int freeSpace = dataNodeMapper.selectFreeSpaceById(chose);
        if(freeSpace < size) {
            do {
                chose += 1;
                if (chose == 6) {
                    chose = 1;
                }
                freeSpace = dataNodeMapper.selectFreeSpaceById(chose);
            } while (freeSpace <= size);
        }
        return chose;
    }

    // 选择第二台和第三胎服务器
    public int[] selectServer23(int server1, int size) {
        int[] res = new int[2];
        List<DataNode> dataNodes = dataNodeMapper.selectAllSpaces();
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < dataNodes.size(); i++) {
            DataNode dataNode = dataNodes.get(i);
            int id = dataNode.getId();
            int freeSpace = dataNode.getFreeSpace();
            if(id != server1) {
                map.put(id, freeSpace);
            }
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet()); //将map的entryset放入list集合
        //对list进行排序，并通过Comparator传入自定义的排序规则
        Collections.sort(list,new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                //重写排序规则，小于0表示升序，大于0表示降序
                return o2.getValue() - o1.getValue();
            }
        });
        for(int i = 0; i <= 1; i++) {
            int key = list.get(i).getKey();
            res[i] = key;
        }
        return res;
    }

    public static void main(String[] args) {
        File file = new File("D:/aaaaa.txt");
        String res = md5(file);
        System.out.println(res);
    }

}
