package com.lon.lin.mutal;

import com.alibaba.fastjson.JSONObject;
import com.lon.lin.encrypt.EncryptInterface;
import com.lon.lin.util.AlgorithmUtil;
import com.lon.lin.util.JSONPrintUtil;
import com.lon.lin.util.ReadKeyUtil;
import com.lon.lin.util.SortUtil;
import com.sun.jmx.snmp.ThreadContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

public class ExecuteRequest {
    private final static Logger log = LoggerFactory.getLogger(ExecuteRequest.class);
    public static Properties properties;
    private static Map<String,String> ENVIRMOENT;
    static {
        String resources = "config.properties";
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Resources.getResourceAsStream(resources);
            properties = new Properties();
            properties.load(resourceAsStream);
            ENVIRMOENT = new HashMap<>();
            ENVIRMOENT.put("218.76.54.229:16610","开发环境");
            ENVIRMOENT.put("218.76.54.231:16610","中台SIT-1环境");
            ENVIRMOENT.put("218.76.54.231:16611","中台SIT-2环境");
            ENVIRMOENT.put("218.76.54.230:16611","中台UAT环境");
        } catch (IOException e) {
            log.error("初始化配置文件config.properties失败",e.getMessage());
        }finally {
            try {
                if (resourceAsStream!=null) {
                    resourceAsStream.close();
                }
            } catch (IOException e) {
                log.error("初始化配置文件",e.getMessage());
            }
        }

    }
    public static String executeRequest(String requestData, String requestUrl, EncryptInterface encryptInterface){
        String endData = encryptInterface.encryptData(requestData);
        String resonseData = postRequest(endData, requestUrl);
        if (resonseData!=null){
            return encryptInterface.decryptData(resonseData);
        }
        return null;
    }


    private static String postRequest(String endData,String requestUrl) {
        HttpEntity responseEntity = null;
        try {
            String url = "http://"+properties.getProperty("ip")+":"+properties.getProperty("port","80")+requestUrl;
            log.info("请求地址："+url);
            ThreadContext.push("ip","aaaa");
            log.info("请求环境："+ENVIRMOENT.get(properties.getProperty("ip")+":"+properties.getProperty("port","80")));
            log.info("请求数据："+ endData);
            HttpPost httpPost = new HttpPost(url);
            StringEntity requestEntiy = new StringEntity(endData);
            httpPost.setEntity(requestEntiy);
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            responseEntity= httpResponse.getEntity();
            String responseMessage = EntityUtils.toString(responseEntity);
            if(statusCode!=200){
                log.error("请求失败，响应码为："+statusCode+"响应消息："+responseMessage);
                return null;
            }
            return responseMessage;
        } catch (UnsupportedEncodingException e) {
           log.error("编码转换异常：",e.getMessage());
        } catch (ClientProtocolException e) {
           log.error("协议异常："+e.getMessage());
        } catch (IOException e) {
            log.error("网络异常："+e.getMessage());
        } finally {
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                log.error("http请求释放资源异常",e);
            }
        }
        return null;
    }


}
