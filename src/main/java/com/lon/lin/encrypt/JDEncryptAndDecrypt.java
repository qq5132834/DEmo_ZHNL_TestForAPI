package com.lon.lin.encrypt;

import com.alibaba.fastjson.JSONObject;
import com.lon.lin.mutal.ExecuteRequest;
import com.lon.lin.util.AlgorithmUtil;
import com.lon.lin.util.JSONPrintUtil;
import com.lon.lin.util.ReadKeyUtil;
import com.lon.lin.util.SortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JDEncryptAndDecrypt implements EncryptInterface {
    private final static Logger log = LoggerFactory.getLogger(JDEncryptAndDecrypt.class);
    @Override
    public String encryptData(Object requestData) {

        try {
            JSONObject headData = new JSONObject();
            headData.put("randomKeyEncrypted","");
            String channelNo = ExecuteRequest.properties.getProperty("channelNo");
            headData.put("channelId",channelNo);
            String requestId = UUID.randomUUID().toString();
            log.info("本笔交易流水号："+requestId);
            log.info("当前交易渠道号："+channelNo);
            headData.put("requestId",requestId);
            headData.put("interfaceId", "UTF-8");
            headData.put("timestamp", new Date().getTime());
            headData.put("bizData", requestData);
            log.info("加密前初始数据："+JSONPrintUtil.format((String) requestData));
            Map<String, String> publicKeyMap = ReadKeyUtil.getInstance().getPublicKeyMap();
            Map<String, String> privateKeyMap = ReadKeyUtil.getInstance().getPrivateKeyMap();
            String aesRandomKey = AlgorithmUtil.getAESRandomKey();
            String randomKeyEncrypted = AlgorithmUtil.encryptWithRSA(aesRandomKey,publicKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));
            String parameterJsonEncrypted = AlgorithmUtil.encryptWithAES((String) requestData, aesRandomKey);

            headData.put("randomKeyEncrypted",randomKeyEncrypted);
            headData.put("bizData",parameterJsonEncrypted);

            String signData = AlgorithmUtil.sign(SortUtil.getNatureSortedJSONObject(headData).toJSONString(), privateKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));


            headData.put("signature",signData);
            log.info("加密后数据："+headData.toJSONString());
            return headData.toJSONString();
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
            log.info("返回码："+responseData.getString("returnCode")+";响应消息："+responseData.getString("returnMsg"));
            Map<String, String> publicKeyMap = ReadKeyUtil.getInstance().getPublicKeyMap();
            Map<String, String> privateKeyMap = ReadKeyUtil.getInstance().getPrivateKeyMap();
            String signature = responseData.getString("signature");
            JSONObject tempData = SortUtil.getNatureSortedJSONObject(responseData).fluentRemove("signature");
            boolean sign = AlgorithmUtil.verify(tempData.toJSONString(),publicKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")), signature);
            if (sign){
                log.info("本次请求，验签成功");
            }else {
                log.info("本次请求，验签失败");
                return responseData.getString("code");
            }
            String randomKeyEncrypted = responseData.getString("randomKeyEncrypted");
            String randomKey = AlgorithmUtil.decryptWithRSA(randomKeyEncrypted, privateKeyMap.get(ExecuteRequest.properties.getProperty("channelNo")));
            String bizData = AlgorithmUtil.decryptWithAES(responseData.getString("bizData"), randomKey);
            log.info("响应数据："+ JSONPrintUtil.format(bizData));
            responseData.put("bizData", bizData);
            responseData.getString("code");
        } catch (Exception e) {
            log.error("验签异常：",e.getMessage());
        }
        return responseData.getString("bizData");
    }
}
