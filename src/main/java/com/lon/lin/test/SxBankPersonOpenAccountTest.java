package com.lon.lin.test;

import com.alibaba.fastjson.JSONObject;
import com.lon.lin.encrypt.JDEncryptAndDecrypt;
import com.lon.lin.mutal.ExecuteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

/***
 * 个人开户信息
 */
public class SxBankPersonOpenAccountTest {

    private final static Logger log = LoggerFactory.getLogger(SxBankPersonOpenAccountTest.class);

    @ParameterizedTest
    @DisplayName("个人客户开户")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void openPerAcc() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/openPerAcc";//reqURL
        requestData.put("channelCode","AKKJ00000");//平台编码 平台注册时，银行分配的平台唯一标识
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranSeqNum", UUID.randomUUID().toString().replace("-",""));//平台测测流水号
        requestData.put("certTypeCode","0");//证件类型 0：身份证；
        requestData.put("linkedAggrType","01");//鉴权类型  01:认证绑定 02:代扣协议 (如无特殊要求，请传01)

        requestData.put("certNo","33010019900530156X");//证件号 一期传身份证件号码
        requestData.put("customerName","伊丽莎白");//账号名称 传开户本人身份证上的姓名
        requestData.put("linkedBankFlag","02");//绑定卡本他行标志 01：本行卡 02：他行卡 （湖南三湘银行卡请传01，其余传02）
        requestData.put("linkedAcctBankCode","102100099996");//绑定账户开户行行号 102100099996-323551000015（每个银行唯一对应的银行行号，通过3.4.2行名行号接口可以查询）
        requestData.put("linkedAcctBankName","中国工商银行");//绑定账户开户行行名 当linkedBankFlag为02则必输（每个银行都有自己的银行名称，例如中国工商银行）
        requestData.put("linkedBaseAcctNo","6222357005000008");//绑定账户账号 绑定的银行卡号，例如：6212261904001484271
        requestData.put("linkedAcctName","伊丽莎白");//绑定账户名称
        requestData.put("teleNo","18100000004");//绑定手机号
        requestData.put("smsCode","111101");//验证码  每次开户提交前需要先通过3.4.1获取短信验证码接口获取验证码然后填入此字段，例如：235464

        JSONObject imageList = new JSONObject();//审核图像数组  开户提交前需要调用3.4.3图片上传接口把身份证件上传，然后银行会返回一个图片地址，此数组用来放身份证件照片类型和地址数组
        imageList.put("imageType","01");// 图片类型 01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList.put("imageUrl","http://10.7.1.88:8080/image/20210316/0040002-OPENAPI001-20210316112943-035460/image/伊丽莎白身份证正面.jpg");//图片地址
        JSONObject imageList2 = new JSONObject();//审核图像数组  开户提交前需要调用3.4.3图片上传接口把身份证件上传，然后银行会返回一个图片地址，此数组用来放身份证件照片类型和地址数组
        imageList2.put("imageType","02");// 图片类型 01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList2.put("imageUrl","http://10.7.1.88:8080/image/20210316/0040002-OPENAPI001-20210316113135-035504/image/伊丽莎白身份证反面.jpg");//图片地址

        ArrayList list =new ArrayList<JSONObject>();
        list.add(imageList);
        list.add(imageList2);

        requestData.put("imageList",list);

        requestData.put("acctType", "0235"); //0235-个人普通账户, 0244-个人简易账户;（不传则默认0235，需上送影像图片）

        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

    //1003
    @ParameterizedTest
    @DisplayName("个人客户查询")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void searchPerCustInfo() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/searchPerCustInfo";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测流水号  平台侧唯一标识一笔交易
        requestData.put("channelCode","AKKJ00000");//平台编码 平台注册时，银行分配的平台唯一标识
        requestData.put("finAccountId","0070018700002060008");//账号0070018700002310000 刘聪
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

    //1006
    @ParameterizedTest
    @DisplayName("个人开户回查")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCsearchAcc() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCsearchAcc";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测流水号  平台侧唯一标识一笔交易
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");
        requestData.put("oldTranSeqNum","2bd35a3593b44b0e9bbf6c9dff319cf2");//原开户平台侧流水号  需要回查的原交易平台流水号
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

    //1007
    @ParameterizedTest
    @DisplayName("个人更换绑定卡")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCupdateCard() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCupdateCard";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测流水号  平台侧唯一标识一笔交易
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二期渠道
        requestData.put("finAccountId","0070018700000000149");//账号
        requestData.put("linkedAggrType","01");////鉴权类型  01:认证绑定 02:代扣协议 (如无特殊要求，请传01)
        requestData.put("linkedBankFlag","01");//绑定卡本他行标志 01：本行卡 02：他行卡 （湖南三湘银行卡请传01，其余传02）
        requestData.put("linkedAcctBankCode","323551000015");//绑定账户开户行行号 102100099996-323551000015（每个银行唯一对应的银行行号，通过3.4.2行名行号接口可以查询）
        requestData.put("linkedAcctBankName","湖南三湘银行");//绑定账户开户行行名 当linkedBankFlag为02则必输（每个银行都有自己的银行名称，例如中国工商银行）
        requestData.put("linkedBaseAcctNo","6236430001000365488");//绑定账户账号 绑定的银行卡号，例如：6212261904001484271
        requestData.put("linkedAcctName","付志滔");//绑定账户名称
        requestData.put("teleNo","13722220000");//绑定手机号
        requestData.put("verifyCode","123456");//验证码  每次开户提交前需要先通过3.4.1获取短信验证码接口获取验证码然后填入此字段，例如：235464
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

    }
    //1008
    @ParameterizedTest
    @DisplayName("个人修改手机号码")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCupdateMobile() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCupdateMobile";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测流水号  平台侧唯一标识一笔交易
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二期渠道
        requestData.put("finAccountId","0070018700000000149");//账号
        requestData.put("mobile","13722220000");//绑定手机号
        requestData.put("newVerifyCode","123456");//验证码  每次开户提交前需要先通过3.4.1获取短信验证码接口获取验证码然后填入此字段，例如：235464
        requestData.put("oldVerifyCode","123456");//sit测试需要去掉
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


}
