package com.lon.lin.util;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ReadKeyUtil {
    private final static Logger log = LoggerFactory.getLogger(ReadKeyUtil.class);

    private Map<String,String> publicKeyMap = new HashMap<>();
    private Map<String,String> privateKeyMap = new HashMap<>();
    private static ReadKeyUtil readKeyUtil;
    private ReadKeyUtil(){}

    public static  ReadKeyUtil getInstance(){
        if (readKeyUtil==null) {
            readKeyUtil = new ReadKeyUtil();
            readKeyUtil.init();
        }
        return readKeyUtil;
    }

    private void init() {
        String resources = "security";
        try {
            File file = new File(Resources.getResourceURL(resources).getPath());
            Arrays.asList(file.listFiles()).forEach(new Consumer<File>() {
                @Override
                public void accept(File file) {
                    String name = file.getName();
                    String publicKey = new File(file.getPath() + "//public.key").getPath();
                    String privateKey = new File(file.getPath() + "//private.key").getPath();
                    privateKeyMap.put(name,readPrivateKeyByPath(privateKey));
                    publicKeyMap.put(name,readPublicKeyByPath(publicKey));
                }
            });
        } catch (IOException e) {
            log.error("读取密钥工具类初始化失败："+e.getMessage());
        }
    }


    public static String readPrivateKeyByPath(String path) {
        try {
            String privateKey = FileUtils.readFileToString(new File(path),"UTF-8");
            privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
            privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");
            return privateKey.replaceAll("\n", "");
        } catch (IOException e) {
            log.error("读取私钥发生异常："+e.getMessage());
        }
        return null;
    }

    public static String readPublicKeyByPath(String path) {
        try {
            String publickey = FileUtils.readFileToString(new File(path),"UTF-8");
            publickey = publickey.replace("-----BEGIN CERTIFICATE REQUEST-----", "");
            publickey = publickey.replace("-----END CERTIFICATE REQUEST-----", "");
            return publickey.replaceAll("\n", "").trim();
        } catch (IOException e) {
            log.error("读取公钥发生异常："+e.getMessage());
        }
        return null;
    }
    public static String getFileString(String path) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String s = "";
        File imgFile = new File(path);
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        try {
            s = new String(org.apache.commons.codec.binary.Base64.encodeBase64String(data));
        } catch (Exception e) {
           log.error("转换异常:",e.getMessage());
        }
        return s;

    }
    public Map<String, String> getPublicKeyMap() {
        return publicKeyMap;
    }

    public void setPublicKeyMap(Map<String, String> publicKeyMap) {
        this.publicKeyMap = publicKeyMap;
    }

    public Map<String, String> getPrivateKeyMap() {
        return privateKeyMap;
    }

    public void setPrivateKeyMap(Map<String, String> privateKeyMap) {
        this.privateKeyMap = privateKeyMap;
    }
}
