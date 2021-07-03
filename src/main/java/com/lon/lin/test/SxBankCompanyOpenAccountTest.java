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

public class SxBankCompanyOpenAccountTest {

    private final static Logger log = LoggerFactory.getLogger(SxBankCompanyOpenAccountTest.class);

    //2001
    @ParameterizedTest
    @DisplayName("企业客户开户")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1) //张可爱 130562198810107080 91370921MA3N29UW1Q
    public void ZHNLSCopenCorAcc() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCopenCorAcc";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranSeqNum", UUID.randomUUID().toString().replace("-",""));//平台流水号  平台侧唯一标识一笔交易
        requestData.put("commissionSign","1");//经办人标识 0或不输：非代理人开户 1：代理人开户
        requestData.put("certTypeCode","260");// 固定值 260-营业执照（三合一）
        requestData.put("certNo","91370921MA3N29UW1Q");//证件号
        requestData.put("customerName","宁阳县志途工程机械设备租赁有限公司");//注册企业名称
        requestData.put("cstAddress","宁阳县");//注册企业地址

        requestData.put("linkedAggrType","01");//鉴权类型
        requestData.put("linkedBankFlag","02");//绑定卡本他行标志 01-本行账户       02-他行账户  （湖南三湘银行账户为本行账户，其它为他行账户）
        requestData.put("linkedAcctBankCode","102100099996");//绑定账户开户行行号 他行必输--323551000015-102100099996
        requestData.put("linkedAcctBankName","中国工商银行");//绑定账户开户行行名 他行必输
        requestData.put("linkedBaseAcctNo","6670010101001002818");//绑定账户账号
        requestData.put("linkedAcctName","宁阳县志途工程机械设备租赁有限公司");//绑定账户名称

//                requestData.put("commissionClientNo","900000261049000002610");//代办人客户号
        requestData.put("commissionClientName","张可爱");//经办人姓名 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
        requestData.put("commissionClientGlobIdType","0");//经办人证件类型 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
        requestData.put("commissionClientGlobId","130562198810107080");//经办人证件号码 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
        requestData.put("commissionTelNo","17512028610");//经办人电话号码 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
        requestData.put("commissionCountry","156");//代办人国籍

        requestData.put("legalPersonName","张可爱");//法定代表人姓名
        requestData.put("legalPersonIdType","0");//法定代表人证件类型
        requestData.put("legalPersonIdNo","130562198810107080");//法定代表人证件号码 0- 居民身份证   企业客户注册时输入
        requestData.put("legalPersonIdFromDate","20060216");//法人证件开始日期YYYYMMDD
        requestData.put("legalPersonIdExpiringDate","20300616");//法人证件到期日期 YYYYMMDD 长期填写：20991231
        requestData.put("legalPersonMobile","17512028610"); //法人代表手机号
        requestData.put("smsCode","571800");//验证码
        requestData.put("remark","备注一下");//备注
//                requestData.put("teleNo","13817220387");
        //requestData.put("customerName","阿里巴巴");
        //requestData.put("channel","IOP");
        JSONObject imageList = new JSONObject(); //审核图像数组
        imageList.put("imageType","05");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList.put("imageUrl","http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100215-046669/image/张可爱身份证正面.jpg");//图片地址
        ArrayList list =new ArrayList<JSONObject>();
        list.add(imageList);

        JSONObject imageList2 = new JSONObject(); //审核图像数组
        imageList2.put("imageType","06");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList2.put("imageUrl","http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100400-046671/image/张可爱身份证背面.jpg");//图片地址    2
        list.add(imageList2);

        JSONObject imageList3 = new JSONObject(); //审核图像数组
        imageList3.put("imageType","07");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList3.put("imageUrl","http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100215-046669/image/张可爱身份证正面.jpg");//图片地址    2
        list.add(imageList3);

        JSONObject imageList4 = new JSONObject(); //审核图像数组
        imageList4.put("imageType","08");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList4.put("imageUrl","http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100400-046671/image/张可爱身份证背面.jpg");//图片地址    2
        list.add(imageList4);

        JSONObject imageList5 = new JSONObject(); //审核图像数组
        imageList5.put("imageType","09");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList5.put("imageUrl","http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318113122-047118/image/张可爱的营业执照.jpg");//图片地址    2
        list.add(imageList5);
        requestData.put("imageList",list);
        log.info("requestData:" + requestData.toJSONString());
        String bizData = ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        log.info("bizData:" + bizData);
    }


    //2007
    @ParameterizedTest
    @DisplayName("企业开户回查")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCopenAcctCheck () {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCopenAcctCheck";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易
        requestData.put("oldTranSeqNum","02d686270c6649728ed4f6cb53546e9c");//需要回查的原交易 流水号
        //
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }



    //2003
    @ParameterizedTest
    @DisplayName("企业客户信息查询")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCsearchCorCustInfo() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCsearchCorCustInfo";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测流水号  平台侧唯一标识一笔交易
        requestData.put("channelCode","AKKJ00000");//平台编码 平台注册时，银行分配的平台唯一标识
        requestData.put("finAccountId","0070018600002310007");//账号

        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


    //2014
    @ParameterizedTest
    @DisplayName("企业更换绑定卡")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCCorUpdateCard () {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCCorUpdateCard";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易
        requestData.put("finAccountId","0070018600000000221");//账号
        requestData.put("linkedBankFlag","01");//绑定卡本他行标志-本行-01-他行-10
        requestData.put("linkedBaseAcctNo","0070010101000002671");//绑定对公活期户账号
        requestData.put("linkedAcctName","上海烛龙信息科技有限公司");//绑定对公活期户户名
        requestData.put("linkedAcctBankName","湖南三湘银行");//绑定对公活期户开户行行名
        requestData.put("linkedAcctBankCode","323551000015");//绑定对公活期户开户行行号--323551000015-102100099996
        requestData.put("verifyCode","619577");//短信验证码
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

    //2015
    @ParameterizedTest
    @DisplayName("企业更换手机号")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCCorUpdateMobile () {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCCorUpdateMobile";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","FZJ0000001123");//二级平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易
        requestData.put("finAccountId","0070018600000000215");//账号
        requestData.put("oldVerifyCode","568585");//旧手机短信验证码
        requestData.put("mobile","13817220091");//新手机号码
        requestData.put("newVerifyCode","341984");//新手机短信验证码
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

//    @ParameterizedTest
//    @DisplayName("获取短信验证码")
//    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
//    public void ZHNLSCsendSMS() {
//        JSONObject requestData = new JSONObject();
//        String path = "/sdkapi/jdplat/ZHNLSCsendSMS";
//        requestData.put("channelCode","FZJ0000001");//平台编码
//        requestData.put("trantype","1");//0-注册1-消费3-充值4-提现 5-冻结  6-解冻
//        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//
//        requestData.put("telNo","13817220091");//支付账号
//        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
//    }

    //2016
    @ParameterizedTest
    @DisplayName("企业经办人信息更新")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCUpdateAgentInfo () {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCUpdateAgentInfo";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易
        requestData.put("finAccountId","0070018600000000211");//账号
        requestData.put("commissionClientName","李思思");//代办人姓名
        requestData.put("commissionClientGlobIdType","0");//代办人证件类型
        requestData.put("commissionClientGlobId","431224198510220365");//代办人证件号码
        requestData.put("commissionTelNo","13817220370");//代办人电话号码
        requestData.put("verifyCode","123456");//验证码
        requestData.put("remark","备注123");//备注

        JSONObject imageList = new JSONObject(); //审核图像数组
        imageList.put("imageType","07");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList.put("imageUrl","http://10.7.1.87:8080/image/20200824/0040002-OPENAPI001-01-20200824182552-002106.png");//图片地址
        ArrayList list =new ArrayList<JSONObject>();
        list.add(imageList);

        JSONObject imageList2 = new JSONObject(); //审核图像数组
        imageList2.put("imageType","08");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
        imageList2.put("imageUrl","http://10.7.1.87:8080/image/20200824/0040002-OPENAPI001-01-20200824182552-002106.png");//图片地址    2
        list.add(imageList2);

        requestData.put("imageList",list);
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


    //2006
    @ParameterizedTest
    @DisplayName("企业专户查询")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCAcctInfo() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCAcctInfo";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//渠道业务流水号
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("finAccountId","0070018600002310012");//账号  0070018600002110000  0070018001000083486
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


}
