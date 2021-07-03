package com.lon.lin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lon.lin.entry.UserInfo;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TestAPIForJD {
    @BeforeEach
    public void setUp() throws IOException {
        String resources = "userInfo.json";
        String userJson = FileUtils.readFileToString(new File(Resources.getResourceURL(resources).getPath()), "UTF8");
        JSONArray userArray = JSONArray.parseArray(userJson);
        userArray.stream().forEach(users -> {
            UserInfo userInfo = JSONObject.toJavaObject((JSON) users, UserInfo.class);
            TestForJD.user.put(userInfo.getId(), userInfo);
        });
    }

    @ParameterizedTest
    @DisplayName("身份证信息上传")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDRelayImgUpload.csv", numLinesToSkip = 1)
    public void JDRelayImgUpload(String flag, String openId, String electronicCardNo, String bankUserId, String userName
            , String idNo, String telephone, String isMakeUp, String imageOrderNo, String frontImage, String backImage) {
        TestForJD.JDRelayImgUpload(flag, openId, electronicCardNo, bankUserId, userName, idNo, telephone, isMakeUp, imageOrderNo, frontImage, backImage);
    }

    @ParameterizedTest
    @DisplayName("账户能力输出影像上传")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDRelayImgUpload.csv", numLinesToSkip = 1)
    public void AcctOutputImgUploadService() {
        TestForJD.AcctOutputImgUploadService();
    }

    @ParameterizedTest
    @DisplayName("账户开户")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDAcctOpen.csv", numLinesToSkip = 1)
    public void testJDAcctOpen(String flag, String openId, String idNo, String customerName, String bindCard
            , String bindTel, String sex, String signOrg, String signStartDate, String orderNumber, String address, String validDate, String occupation) {
        TestForJD.JDAcctOpen(flag, openId, idNo, customerName, bindCard, bindTel, sex, signOrg, signStartDate, orderNumber, address, validDate, occupation);
    }

    @ParameterizedTest
    @DisplayName("账户信息查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDAcctMsgQry.csv", numLinesToSkip = 1)
    public void testJDAcctMsgQry(String flag, String openId, String electronicCardNo, String bankUserId, String customerName, String idNo) {
        TestForJD.JDAcctMsgQry(flag, openId, electronicCardNo, bankUserId, customerName, idNo);
    }

    @ParameterizedTest
    @DisplayName("换绑卡")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDReplaceCard.csv", numLinesToSkip = 1)
    public void testJDReplaceCard(String flag, String openID, String bankUserId, String electronicCardNo, String customerName, String idNo, String bindCard, String bindTel, String oldBindCard, String oldBindTel, String jdTradeNumber) {
        TestForJD.JDReplaceCard(flag, openID, bankUserId, electronicCardNo, customerName, idNo, bindCard, bindTel, oldBindCard, oldBindTel, jdTradeNumber);
    }

    @ParameterizedTest
    @DisplayName("换绑手机号")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDReplacePhone.csv", numLinesToSkip = 1)
    public void testJDReplacePhone(String flag, String openId, String bankUserId, String customerName, String idNo, String oldTelNo, String newTelNo, String tradeNoJD) {
        TestForJD.JDReplacePhone(flag, openId, bankUserId, customerName, idNo, oldTelNo, newTelNo, tradeNoJD);
    }

    @ParameterizedTest
    @DisplayName("京东充值")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDRecharge.csv", numLinesToSkip = 1)
    public void testJDRechage(String flag, String openId, String chargeOrderNumber, String electronicCardNo, String bankUserId, String customerName, String idNo, String amount, String jdTradeNumber, String bankCard) {
        TestForJD.JDRechage(flag, openId, chargeOrderNumber, electronicCardNo, bankUserId, customerName, idNo, amount, jdTradeNumber, bankCard);
    }

    @ParameterizedTest
    @DisplayName("京东提现")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDWithdraw.csv", numLinesToSkip = 1)
    public void JDWithDraw(String flag, String openId, String withdrawOrderNumber, String electronicCardNo, String bankUserId, String customerName, String idNo, String amount, String jdTradeNumber, String bankCard) {
        TestForJD.JDWithDraw(flag, openId, withdrawOrderNumber, electronicCardNo, bankUserId, customerName, idNo, amount, jdTradeNumber, bankCard);
    }

    @ParameterizedTest
    @DisplayName("京东提现查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDWithdrawQry.csv", numLinesToSkip = 1)
    public void JDWithDrawQry(String flag, String openId, String withdrawOrderNumber) {
        TestForJD.JDWithDrawQry(flag, openId, withdrawOrderNumber);
    }

    @ParameterizedTest
    @DisplayName("京东充值查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDRechargeQry.csv", numLinesToSkip = 1)
    public void JDRechrgeQry(String flag, String openId, String withdrawOrderNumber) {
        TestForJD.JDRechrgeQry(flag, openId, withdrawOrderNumber);
    }

    @ParameterizedTest
    @DisplayName("京东智享存产品查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductQry.csv", numLinesToSkip = 1)
    public void JDZXCProductQry(String flag, String productCode) {
        TestForJD.JDZXCProductQry(flag, productCode);
    }

    @ParameterizedTest
    @DisplayName("京东智享存产品购买")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductBuy.csv", numLinesToSkip = 1)
    public void JDZXCProductBuy(String flag, String openId, String acNo, String bankUserId, String productCode, String orderNo, String tradeAmount) {
        TestForJD.JDZXCProductBuy(flag, openId, acNo, bankUserId, productCode, orderNo, tradeAmount);
    }

    @ParameterizedTest
    @DisplayName("京东智享存产品赎回")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductRedeem.csv", numLinesToSkip = 1)
    public void JDZXCProductRedeem(String flag, String openId, String acNo, String bankUserId, String orderNo, String bankOrderNo, String redeemNo, String principal) {
        TestForJD.JDZXCProductRedeem(flag, openId, acNo, bankUserId, orderNo, bankOrderNo, redeemNo, principal);
    }

    @ParameterizedTest
    @DisplayName("京东智享存购买查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductBuyQry.csv", numLinesToSkip = 1)
    public void JDZXCProductBuyQry(String flag, String openId, String acNo, String bankUserId, String orderNo, String bankOrderNo) {
        TestForJD.JDZXCProductBuyQry(flag, openId, acNo, bankUserId, orderNo, bankOrderNo);
    }

    @ParameterizedTest
    @DisplayName("京东智享存赎回查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductRedeemQry.csv", numLinesToSkip = 1)
    public void JDZXCProductRedeemQry(String flag, String openId, String acNo, String bankUserId, String orderNo, String bankOrderNo, String redeemNo, String bankRedeemNo) {
        TestForJD.JDZXCProductRedeemQry(flag, openId, acNo, bankUserId, orderNo, bankOrderNo, redeemNo, bankRedeemNo);
    }

    @ParameterizedTest
    @DisplayName("京东智享存收益试算")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductProfit.csv", numLinesToSkip = 1)
    public void JDZXCProductProfit(String flag, String openId, String acNo, String bankUserId, String orderNo, String bankOrderNo, String principal) {
        TestForJD.JDZXCProductProfit(flag, openId, acNo, bankUserId, orderNo, bankOrderNo, principal);
    }

    @ParameterizedTest
    @DisplayName("京东智享存派息列表查询")
    @CsvFileSource(resources = "/paramsFile/JDParams/JDZXCProductPayList.csv", numLinesToSkip = 1)
    public void JDZXCProductPayList(String flag, String openId, String acNo, String bankUserId, String orderNo, String bankOrderNo) {
        TestForJD.JDZXCProductPayList(flag, openId, acNo, bankUserId, orderNo, bankOrderNo);
    }



}
