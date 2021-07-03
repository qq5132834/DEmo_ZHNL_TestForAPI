package com.lon.lin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lon.lin.encrypt.EncryptInterface;
import com.lon.lin.encrypt.JDEncryptAndDecrypt;
import com.lon.lin.encrypt.JDZXCEncryptAndDecrypt;
import com.lon.lin.entry.UserInfo;
import com.lon.lin.mutal.ExecuteRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.platform.commons.util.StringUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.lon.lin.util.ReadKeyUtil.getFileString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestForJD {
    public static Map<String, UserInfo> user = new HashMap<>();
    private static EncryptInterface encryptInterface = new JDEncryptAndDecrypt();
    private static EncryptInterface zxcencryptInterface = new JDZXCEncryptAndDecrypt();

    public static void JDRelayImgUpload(String flag, String openId, String electronicCardNo, String bankUserId, String userName
            , String idNo, String telephone, String isMakeUp, String imageOrderNo, String frontImage, String backImage) {
        if ("true".equalsIgnoreCase(flag)) {
            String path = "/sdkapi/jdplat/JDRelayImgUpload";
            JSONObject requestData = new JSONObject();
            requestData.put("openId", openId);
            requestData.put("electronicCardNo", Optional.ofNullable(user.getOrDefault(electronicCardNo, new UserInfo()).getAcctNo()).orElse(electronicCardNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("userName", Optional.ofNullable(user.getOrDefault(userName, new UserInfo()).getUsername()).orElse(userName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("telephone", Optional.ofNullable(user.getOrDefault(telephone, new UserInfo()).getPhone()).orElse(telephone));
            requestData.put("isMakeUp", isMakeUp);
            requestData.put("imageOrderNo", StringUtils.isBlank(imageOrderNo)?UUID.randomUUID().toString():imageOrderNo);
            requestData.put("frontImage", getFileString(frontImage));
            requestData.put("backImage", getFileString(backImage));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }


    public static void AcctOutputImgUploadService  () {

            String path = "/sdkapi/jdplat/AcctOutputImgUploadService";
            JSONObject requestData = new JSONObject();

            requestData.put("openId", "152200034");
            requestData.put("electronicCardNo", "33010019900530156X");
            requestData.put("ChannelNo", "0040002");
            requestData.put("customerName", "伊丽莎白");

            //requestData.put("channelNo ", "0080034");
//            requestData.put("Channeld ", "0040002");
            requestData.put("Flownum", "IOP20200824152261");
            requestData.put("imgId", "1522061");
            requestData.put("imgName", "伊丽莎白身份证反面.jpg");
            requestData.put("IdentCrdPht", getFileString("D:\\Program Files\\nginx-1.12.2\\nginx-1.12.2\\html\\ylsb-fan.jpg"));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);

    }



    public static void JDAcctOpen(String flag, String openId, String idNo, String customerName, String bindCard
            , String bindTel, String sex, String signOrg, String signStartDate, String orderNumber, String address, String validDate, String occupation) {
        if ("true".equalsIgnoreCase(flag)) {
            String path = "/sdkapi/jdplat/JDAcctOpen";
            JSONObject requestData = new JSONObject();
            requestData.put("openId", openId);
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("bindCard", Optional.ofNullable(user.getOrDefault(bindCard, new UserInfo()).getBankCard()).orElse(bindCard));
            requestData.put("bindTel", Optional.ofNullable(user.getOrDefault(bindTel, new UserInfo()).getPhone()).orElse(bindTel));
            requestData.put("sex", sex);
            requestData.put("signOrg", signOrg);
            requestData.put("signStartDate", signStartDate);
            requestData.put("orderNumber", StringUtils.isBlank(orderNumber)?UUID.randomUUID().toString():orderNumber);
            requestData.put("address", address);
            requestData.put("validDate", validDate);
            requestData.put("occupation", occupation);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }

    }


    public static void JDAcctMsgQry(String flag, String openId, String electronicCardNo, String bankUserId, String customerName, String idNo) {
        if ("true".equalsIgnoreCase(flag)) {
            String path = "/sdkapi/jdplat/JDAcctMsgQry";
            JSONObject requestData = new JSONObject();
            requestData.put("openId", openId);
            requestData.put("electronicCardNo", Optional.ofNullable(user.getOrDefault(electronicCardNo, new UserInfo()).getAcctNo()).orElse(electronicCardNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }

    public static void JDReplaceCard(String flag, String openID, String bankUserId,String electronicCardNo, String customerName, String idNo, String bindCard, String bindTel, String oldBindCard, String oldBindTel ,String jdTradeNumber) {
        if ("true".equalsIgnoreCase(flag)) {
            String path = "/sdkapi/jdplat/JDMCmbAcctMntn";
            JSONObject requestData = new JSONObject();
            requestData.put("openId", openID);
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("electronicCardNo", Optional.ofNullable(user.getOrDefault(electronicCardNo, new UserInfo()).getAcctNo()).orElse(electronicCardNo));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("bindCard", Optional.ofNullable(user.getOrDefault(bindCard, new UserInfo()).getBankCard()).orElse(bindCard));
            requestData.put("bindTel", Optional.ofNullable(user.getOrDefault(bindTel, new UserInfo()).getPhone()).orElse(bindTel));
            requestData.put("oldBindCard", oldBindCard);
            requestData.put("oldBindTel", oldBindTel);
            requestData.put("jdTradeNumber",StringUtils.isBlank(jdTradeNumber)?UUID.randomUUID().toString():jdTradeNumber);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }


    public static void JDReplacePhone(String flag,String openId ,String bankUserId ,String customerName,String idNo,String oldTelNo,String  newTelNo ,String tradeNoJD) {
        if ("true".equalsIgnoreCase(flag)) {
            String path = "/sdkapi/jdplat/JDChangeMoble";
            JSONObject requestData = new JSONObject();
            requestData.put("openId", openId);
            requestData.put("bankUserId",Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("oldTelNo", oldTelNo);
            requestData.put("newTelNo",Optional.ofNullable(user.getOrDefault(newTelNo, new UserInfo()).getPhone()).orElse(newTelNo));
            requestData.put("tradeNoJD",StringUtils.isBlank(tradeNoJD)?UUID.randomUUID().toString():tradeNoJD);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }

    public static void JDRechage(String flag,String openId,String chargeOrderNumber,String electronicCardNo,String bankUserId,String customerName,String idNo,String amount,String jdTradeNumber,String bankCard) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDRecharge";
            requestData.put("openId", openId);
            requestData.put("chargeOrderNumber", StringUtils.isBlank(chargeOrderNumber)?UUID.randomUUID().toString():chargeOrderNumber);
            requestData.put("electronicCardNo", Optional.ofNullable(user.getOrDefault(electronicCardNo, new UserInfo()).getAcctNo()).orElse(electronicCardNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("amount", amount);
            requestData.put("jdTradeNumber", StringUtils.isBlank(jdTradeNumber)?UUID.randomUUID().toString():jdTradeNumber);
            requestData.put("bankCard", Optional.ofNullable(user.getOrDefault(bankCard, new UserInfo()).getBankCard()).orElse(bankCard));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }

    public static void JDWithDraw(String flag,String openId,String withdrawOrderNumber,String electronicCardNo,String bankUserId,String customerName,String idNo,String amount,String jdTradeNumber,String bankCard) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDWthdrwDpsit";
            requestData.put("openId", openId);
            requestData.put("withdrawOrderNumber",  StringUtils.isBlank(withdrawOrderNumber)?UUID.randomUUID().toString():withdrawOrderNumber);
            requestData.put("electronicCardNo", Optional.ofNullable(user.getOrDefault(electronicCardNo, new UserInfo()).getAcctNo()).orElse(electronicCardNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("customerName", Optional.ofNullable(user.getOrDefault(customerName, new UserInfo()).getUsername()).orElse(customerName));
            requestData.put("idNo", Optional.ofNullable(user.getOrDefault(idNo, new UserInfo()).getIdentNo()).orElse(idNo));
            requestData.put("amount", amount);
            requestData.put("jdTradeNumber",  StringUtils.isBlank(jdTradeNumber)?UUID.randomUUID().toString():jdTradeNumber);
            requestData.put("bankCard", Optional.ofNullable(user.getOrDefault(bankCard, new UserInfo()).getBankCard()).orElse(bankCard));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }

    public static void JDWithDrawQry(String flag,String openId,String withdrawOrderNumber) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDWhTxnCtfyQry";
            requestData.put("openId", openId);
            requestData.put("withdrawOrderNumber", withdrawOrderNumber);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }

    public static void JDRechrgeQry(String flag,String openId,String withdrawOrderNumber) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDReTxnCtfyQry";
            requestData.put("openId", openId);
            requestData.put("chargeOrderNumber", withdrawOrderNumber);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,encryptInterface);
        }
    }
    public static void JDZXCProductQry(String flag,String productCode) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductQry";
            requestData.put("productCode", productCode);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductBuy(String flag,String openId,String acNo,String bankUserId,String productCode,String orderNo,String tradeAmount) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductBuy";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId,
                    new UserInfo()).getCstNo()).orElse(bankUserId));
            requestData.put("productCode", productCode);
            requestData.put("orderNo", StringUtils.isBlank(orderNo)?UUID.randomUUID().toString():orderNo);
            requestData.put("tradeAmount", tradeAmount);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductRedeem(String flag,String openId,String acNo,String bankUserId,String orderNo,String bankOrderNo,String redeemNo,String principal) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductRedeem";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getAcctNo()).orElse(bankUserId));
            requestData.put("orderNo", orderNo);
            requestData.put("bankOrderNo", bankOrderNo);
            requestData.put("redeemNo", StringUtils.isBlank(redeemNo)?UUID.randomUUID().toString():redeemNo);
            requestData.put("principal", principal);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductBuyQry(String flag,String openId,String acNo,String bankUserId,String orderNo,String bankOrderNo) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductBuyQry";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getAcctNo()).orElse(bankUserId));
            requestData.put("orderNo", orderNo);
            requestData.put("bankOrderNo", bankOrderNo);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductRedeemQry(String flag,String openId,String acNo,String bankUserId,String orderNo,String bankOrderNo,String redeemNo,String bankRedeemNo) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductRedeemQry";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getAcctNo()).orElse(bankUserId));
            requestData.put("orderNo", orderNo);
            requestData.put("bankOrderNo", bankOrderNo);
            requestData.put("redeemNo", redeemNo);
            requestData.put("bankRedeemNo", bankRedeemNo);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductProfit(String flag,String openId,String acNo,String bankUserId,String orderNo,String bankOrderNo,String principal) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductProfit";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getAcctNo()).orElse(bankUserId));
            requestData.put("orderNo", orderNo);
            requestData.put("bankOrderNo", bankOrderNo);
            requestData.put("principal", principal);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductPayList(String flag,String openId,String acNo,String bankUserId,String orderNo,String bankOrderNo) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductPayList";
            requestData.put("openId", openId);
            requestData.put("acNo", Optional.ofNullable(user.getOrDefault(acNo, new UserInfo()).getAcctNo()).orElse(acNo));
            requestData.put("bankUserId", Optional.ofNullable(user.getOrDefault(bankUserId, new UserInfo()).getAcctNo()).orElse(bankUserId));
            requestData.put("orderNo", orderNo);
            requestData.put("bankOrderNo", bankOrderNo);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDZXCProductHoldingQry(String flag,String productCode,String queryDate) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDZXCProductHoldingQry";
            requestData.put("productCode", productCode);
            requestData.put("queryDate", queryDate);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

    public static void JDRelayQry(String flag,String productCode,String queryDate) {
        if ("true".equalsIgnoreCase(flag)) {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/JDRelayQry";
            requestData.put("productCode", productCode);
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,zxcencryptInterface);
        }
    }

}
