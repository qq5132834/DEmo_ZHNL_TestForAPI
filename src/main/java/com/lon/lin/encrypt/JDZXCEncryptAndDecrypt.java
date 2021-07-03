package com.lon.lin.encrypt;

import com.alibaba.fastjson.JSONObject;
import com.lon.lin.mutal.ExecuteRequest;
import com.lon.lin.util.AlgorithmUtil;
import com.lon.lin.util.JSONPrintUtil;
import com.lon.lin.util.ReadKeyUtil;
import com.lon.lin.util.SortUtil;
import org.bouncycastle.util.IPAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JDZXCEncryptAndDecrypt implements EncryptInterface {
    private final static Logger log = LoggerFactory.getLogger(JDZXCEncryptAndDecrypt.class);
    @Override
    public String encryptData(Object requestData) {

        try {
            String channelNo = ExecuteRequest.properties.getProperty("channelNo");
            JSONObject message = new JSONObject();
            JSONObject encryptedData = new JSONObject();
            JSONObject encryptedHead = new JSONObject();
            JSONObject extra = new JSONObject();
            encryptedHead.put("IMEI","1111");
            encryptedHead.put("ip", InetAddress.getLocalHost().getHostAddress());
            encryptedHead.put("address","长沙");
            encryptedHead.put("osType",System.getProperty("os.name"));
            encryptedHead.put("phoneType","aaa");
            encryptedHead.put("mac","1111111");
            extra.put("merchant",channelNo);
            encryptedData.put("body",JSONObject.parseObject((String) requestData));
            encryptedData.put("head",encryptedHead);


            String requestId = UUID.randomUUID().toString();
            log.info("本笔交易流水号："+requestId);
            log.info("当前交易渠道号："+channelNo);
            log.info("加密前初始数据："+JSONPrintUtil.format((String) requestData));
            Map<String, String> publicKeyMap = ReadKeyUtil.getInstance().getPublicKeyMap();
            Map<String, String> privateKeyMap = ReadKeyUtil.getInstance().getPrivateKeyMap();
            String aesRandomKey = AlgorithmUtil.getAESRandomKey();
            String randomKeyEncrypted = AlgorithmUtil.encryptWithRSA(aesRandomKey,publicKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));
            String parameterJsonEncrypted = AlgorithmUtil.encryptWithAES((String) encryptedData.toJSONString(), aesRandomKey);
            message.put("sequenceNo",requestId);
            message.put("timestamp",new Date().getTime());
            message.put("encryptedKey",randomKeyEncrypted);
            message.put("encryptedData",parameterJsonEncrypted);
            message.put("extra",extra);

            String signData = AlgorithmUtil.sign(SortUtil.getNatureSortedJSONObject(message).toJSONString(), privateKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));


            message.put("signature",signData);
            log.info("加密后数据："+message.toJSONString());
            return message.toJSONString();
        } catch (Exception e) {
            log.error("加密失败",e.getMessage());
        }
        return null;
    }

    @Override
    public String decryptData(Object resonseMessage) {
        log.info("解密前初始数据："+resonseMessage);
        JSONObject responseData = JSONObject.parseObject((String) resonseMessage);
        try {
            String signature = (String) responseData.remove("signature");
            Map<String, String> publicKeyMap = ReadKeyUtil.getInstance().getPublicKeyMap();
            Map<String, String> privateKeyMap = ReadKeyUtil.getInstance().getPrivateKeyMap();

            boolean sign = AlgorithmUtil.verify(SortUtil.getNatureSortedJSONObject(responseData).toJSONString(),publicKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")), signature);
            if (sign){
                log.info("本次请求，验签成功");
            }else {
                log.info("本次请求，验签失败");
            }
            String randomKeyEncrypted = responseData.getString("encryptedKey");
            String randomKey = AlgorithmUtil.decryptWithRSA(randomKeyEncrypted, privateKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));
            String busData = AlgorithmUtil.decryptWithAES(responseData.getString("encryptedData"), randomKey);
            JSONObject bodyData = JSONObject.parseObject(busData);
            log.info("返回码："+bodyData.getString("code")+";响应消息："+bodyData.getString("message"));

            log.info("响应数据："+ JSONPrintUtil.format(busData));
        } catch (Exception e) {
            log.error("验签异常：",e.getMessage());
        }
        return "";
    }
}
