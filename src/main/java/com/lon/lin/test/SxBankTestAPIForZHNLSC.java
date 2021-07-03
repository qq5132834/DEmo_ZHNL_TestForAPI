package com.lon.lin.test;

import com.alibaba.fastjson.JSONObject;
import com.lon.lin.encrypt.JDEncryptAndDecrypt;
import com.lon.lin.mutal.ExecuteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.UUID;

import static com.lon.lin.util.ReadKeyUtil.getFileString;

public class SxBankTestAPIForZHNLSC {

    private final static Logger log = LoggerFactory.getLogger(SxBankTestAPIForZHNLSC.class);
        //1001
        @ParameterizedTest
        @DisplayName("个人客户开户")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void openPerAcc() {
                JSONObject requestData = new JSONObject();
                    String path = "/sdkapi/jdplat/openPerAcc";//reqURL
                    requestData.put("channelCode","AKKJ00000");//平台编码 平台注册时，银行分配的平台唯一标识
                    requestData.put("secondChannelCode","");//二级平台编码
                    requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台测测流水号
                    requestData.put("certNo","430621195805297014");//证件号 一期传身份证件号码
                    requestData.put("certTypeCode","0");//证件类型 0：身份证；
                    requestData.put("customerName","黄地军");//账号名称 传开户本人身份证上的姓名
                    requestData.put("linkedAggrType","01");//鉴权类型  01:认证绑定 02:代扣协议 (如无特殊要求，请传01)
                    requestData.put("linkedBankFlag","02");//绑定卡本他行标志 01：本行卡 02：他行卡 （湖南三湘银行卡请传01，其余传02）
                    requestData.put("linkedAcctBankCode","102100099996");//绑定账户开户行行号 102100099996-323551000015（每个银行唯一对应的银行行号，通过3.4.2行名行号接口可以查询）
                    requestData.put("linkedAcctBankName","中国工商银行");//绑定账户开户行行名 当linkedBankFlag为02则必输（每个银行都有自己的银行名称，例如中国工商银行）
                    requestData.put("linkedBaseAcctNo","6215581915162306263");//绑定账户账号 绑定的银行卡号，例如：6212261904001484271
                    requestData.put("linkedAcctName","戴毅林");//绑定账户名称
                    requestData.put("teleNo","17512028610");//绑定手机号
                    requestData.put("smsCode","779227");//验证码  每次开户提交前需要先通过3.4.1获取短信验证码接口获取验证码然后填入此字段，例如：235464

                    JSONObject imageList = new JSONObject();//审核图像数组  开户提交前需要调用3.4.3图片上传接口把身份证件上传，然后银行会返回一个图片地址，此数组用来放身份证件照片类型和地址数组
                    imageList.put("imageType","01");// 图片类型 01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
                    imageList.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093159-783394/image/身份证正面.jpg");//图片地址
                    JSONObject imageList2 = new JSONObject();//审核图像数组  开户提交前需要调用3.4.3图片上传接口把身份证件上传，然后银行会返回一个图片地址，此数组用来放身份证件照片类型和地址数组
                    imageList2.put("imageType","02");// 图片类型 01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
                    imageList2.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093328-783776/image/身份证背面.jpg");//图片地址

                    ArrayList list =new ArrayList<JSONObject>();
                    list.add(imageList);
                    list.add(imageList2);

                    requestData.put("imageList",list);
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
            requestData.put("finAccountId","0070018700002060008");//账号
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
            requestData.put("oldTranSeqNum","02d686270c6649728ed4f6cb53546e9c");//原开户平台侧流水号  需要回查的原交易平台流水号
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


//    5001
    @ParameterizedTest
    @DisplayName("获取短信验证码")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCsendSMS() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCsendSMS";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("trantype","1");//0-注册1-消费3-充值4-提现 5-冻结  6-解冻
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//
        requestData.put("telNo","17512028610");//支付账号
        String bizData = ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        log.info("bizData:" + bizData);
    }


    //2001
    @ParameterizedTest
    @DisplayName("企业客户开户")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCopenCorAcc() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCopenCorAcc";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("secondChannelCode","");//二级平台编码
            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台流水号  平台侧唯一标识一笔交易
            requestData.put("commissionSign","1");//经办人标识 0或不输：非代理人开户 1：代理人开户
            requestData.put("certTypeCode","260");// 固定值 260-营业执照（三合一）
            requestData.put("certNo","91410103317416469Q");//证件号
            requestData.put("customerName","郑州德芙食品有限公司");//注册企业名称
            requestData.put("cstAddress","企业注册地址河南省郑州市");//注册企业地址

            requestData.put("linkedAggrType","01");//鉴权类型
            requestData.put("linkedBankFlag","02");//绑定卡本他行标志 01-本行账户       10-他行账户  湖南三湘银行账户为本行账户，其它为他行账户
            requestData.put("linkedAcctBankCode","102100099996");//绑定账户开户行行号 他行必输--323551000015-102100099996
            requestData.put("linkedAcctBankName","中国工商银行");//绑定账户开户行行名 他行必输
            requestData.put("linkedBaseAcctNo","6670010101001002818");//绑定账户账号
            requestData.put("linkedAcctName","郑州德芙食品有限公司");//绑定账户名称

//                requestData.put("commissionClientNo","900000261049000002610");//代办人客户号
            requestData.put("commissionClientName","黄地军");//经办人姓名 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
            requestData.put("commissionClientGlobIdType","0");//经办人证件类型 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
            requestData.put("commissionClientGlobId","430621195805297014");//经办人证件号码 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
            requestData.put("commissionTelNo","13817220322");//经办人电话号码 若经办人标识为“0或不输：非代理人开户”，则经办人上送法人信息
            requestData.put("commissionCountry","156");//代办人国籍

            requestData.put("legalPersonName","黄地军");//法定代表人姓名
            requestData.put("legalPersonIdType","0");//法定代表人证件类型
            requestData.put("legalPersonIdNo","430621195805297014");//法定代表人证件号码 0- 居民身份证   企业客户注册时输入
            requestData.put("legalPersonIdFromDate","20180503");//法人证件开始日期YYYYMMDD
            requestData.put("legalPersonIdExpiringDate","20991231");//法人证件到期日期 YYYYMMDD 长期填写：20991231
            requestData.put("legalPersonMobile","13817220321"); //法人代表手机号
            requestData.put("smsCode","571800");//验证码
            requestData.put("remark","备注一下");//备注
//                requestData.put("teleNo","13817220387");
            //requestData.put("customerName","阿里巴巴");
            //requestData.put("channel","IOP");
            JSONObject imageList = new JSONObject(); //审核图像数组
            imageList.put("imageType","05");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
//            imageList.put("imageUrl","http://10.7.1.88:8080/image/20201113/0080034-OPENAPI001-20201113145255-075994/image/葛黎昕.bmp");//图片地址
            imageList.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093159-783394/image/身份证正面.jpg");//图片地址
            ArrayList list =new ArrayList<JSONObject>();
            list.add(imageList);

            JSONObject imageList2 = new JSONObject(); //审核图像数组
            imageList2.put("imageType","06");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
//            imageList2.put("imageUrl","http://10.7.1.88:8080/image/20201112/0040002-OPENAPI001-20201112185639-034694/image/国徽面.bmp");//图片地址    2
            imageList2.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093328-783776/image/身份证背面.jpg");//图片地址    2
            list.add(imageList2);

            JSONObject imageList3 = new JSONObject(); //审核图像数组
            imageList3.put("imageType","07");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
            imageList3.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093159-783394/image/身份证正面.jpg");//图片地址    2
            list.add(imageList3);

           JSONObject imageList4 = new JSONObject(); //审核图像数组
           imageList4.put("imageType","08");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
           imageList4.put("imageUrl","http://10.7.1.88:8080/image/20210315/0040002-OPENAPI001-20210315093328-783776/image/身份证背面.jpg");//图片地址    2
           list.add(imageList4);

           JSONObject imageList5 = new JSONObject(); //审核图像数组
          imageList5.put("imageType","09");//图片类型01：身份证人像面 02：身份证国徽面 03：证件影像件（非身份证时必输） 04：纸质材料（非身份证时必输） 05：法人身份证人像面  06：法人身份证国徽面 07：代办人身份证人像面 08：代办人身份证国徽面  09：营业执照影像
          imageList5.put("imageUrl","http://10.7.1.88:8080/image/20210106/0040002-OPENAPI001-20210106142006-712573/image/郑州德芙食品有限公司.jpg");//图片地址    2
           list.add(imageList5);
           requestData.put("imageList",list);
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
        requestData.put("oldTranSeqNum","8566effaec49405788b6cb982d5755a9");//需要回查的原交易 流水号
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
            requestData.put("channelCode","ZYTX00002");//平台编码 平台注册时，银行分配的平台唯一标识
            requestData.put("finAccountId","0070018600000000305");//账号

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
            requestData.put("finAccountId","0070010101000002579");//账号
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }



        //3001
        @ParameterizedTest
        @DisplayName("账户消费")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCtrade() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCtrade";

            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("merchantOrderId",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号）
            requestData.put("tranType","1");//交易类型 1-消费 2-退款 7-奖励金发放   8-分账

            requestData.put("amount","18");//支付金额
            requestData.put("accountNo","0070018600002110000");//支付账号
            requestData.put("accountName","宁阳县志途工程机械设备租赁有限公司");//支付账户户名
            requestData.put("oppAccountNo","0070018700002060008");//对手支付账号
            requestData.put("oppAccountName","伊丽莎白");//对手支付账户户名
            requestData.put("orderTime","20210922180606"); //yyyyMMddHHmmss
            requestData.put("smsCode","111111");//短信验证码
//            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
//d70416e24d3c4a18adaec355de68e53e
        }

        //3002
        @ParameterizedTest
        @DisplayName("交易明细")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCtradeDetail() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCtradeDetail";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧流水号 平台侧唯一标识一笔交易
            requestData.put("finAccountId","0070018600002060001");//商户 0070018600002110000 个人-伊丽莎白  0070018700002060008
            requestData.put("beginTime","20210601");//yyyyMMdd，以交易银行返回的交易日期为准
            requestData.put("endTime","20210624");//yyyyMMdd，以交易银行返回的交易日期为准
//            requestData.put("tranType","1");//1-消费 2-退款 3-充值 4-提现  7-奖励金发放 8-分账


            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }


        //3005
        @ParameterizedTest
        @DisplayName("专户交易明细")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCAcctTradeDetail() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCAcctTradeDetail";
            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧流水号
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("finAccountId","0070010101000002579");//主账户
            requestData.put("parentTranType","0");//交易类型分组 0-存款+取款（默认) 1-存款2-取款
            requestData.put("startDate","20200318");//开始日期
            requestData.put("endDate","20240322");//结束日期
            requestData.put("pageSize","10");//结束日期
            requestData.put("pageNum","1");//结束日期
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }



        //3007
        @ParameterizedTest
        @DisplayName("充值提现-资金动账")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCRecharge() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCRecharge";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("tranType","4");// 3-资金入账（目前暂不支持此交易） 4-资金提现
            requestData.put("merchantOrderId",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
            requestData.put("amount","100.01");//支付金额
            requestData.put("accountNo","0070018700002060008");//支付账号
            requestData.put("accountName","伊丽莎白");//支付账户户名
            requestData.put("oppAccountNo","6222357005000008");//对手支付账号
            requestData.put("oppAccountName","伊丽莎白");//对手支付账户户名
            requestData.put("orderTime","20200923170222");//
            requestData.put("smsCode","640187");//短信验证码

            //requestData.put("orderTime","20200726180606");//是否商户上送？
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

        }

//        @ParameterizedTest
//        @DisplayName("获取短信验证码")
//        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
//        public void ZHNLSCsendSMS() {
//            JSONObject requestData = new JSONObject();
//            String path = "/sdkapi/jdplat/ZHNLSCsendSMS";
//            requestData.put("trantype","0");//0-注册1-消费3-充值4-提现 5-冻结  6-解冻
//            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//
//            requestData.put("telNo","13817223912");//支付账号
//            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
//        }

        //3008
        @ParameterizedTest
        @DisplayName("冻结解冻")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCfrozen() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCfrozen";
            requestData.put("channelCode","AKKJ00000");//平台编码 HYZ00001
            requestData.put("merchantOrderId",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
            requestData.put("tranType","5");//交易类型 5-冻结 6-解冻
            requestData.put("frozenOrderId","64dc8b0e7f1e42a0980989851ca2a8a7");// 原冻结商户订单号
            requestData.put("amount","12");//支付金额
            requestData.put("freezeStartDate","20201222");//若交易类型为“5-冻结”时，为必填
            requestData.put("freezeDueDate","20201222");//
            requestData.put("accountNo","0070018700000000329");//支付账号
            requestData.put("accountName","周武");//支付账户户名
            requestData.put("orderTime","20200818155500");
            requestData.put("smsCode","111111");//短信验证码
//            requestData.put("freezeDueDate","20200807");
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }

        //3006
        @ParameterizedTest
        @DisplayName("冻结解冻查询")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCfrozenQuery() {
          JSONObject requestData = new JSONObject();
          String path = "/sdkapi/jdplat/ZHNLSCfrozenQuery";
          requestData.put("channelCode","AKKJ00000");//平台编码
          requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));// 平台侧流水号 平台侧唯一标识一笔交易
          requestData.put("finAccountId","0070018600000000120");//账号：0070018600000000120-企业，0070018700000000112-个人
          requestData.put("tranType","5");//5-冻结 6-解冻
          requestData.put("beginDate","20200825");//开始日期 yyyMMdd，以交易银行返回的交易日期为准
          requestData.put("endDate","20200825");//结束日期 yyyyMMdd，以交易银行返回的交易日期为准
          ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

          }

        //3009
        @ParameterizedTest
        @DisplayName("交易回查")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCtradeCheck() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCtradeCheck";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧流水号
            requestData.put("oldMerchantOrderId","14ff6b3506e64b88a9138eb8d98e6216");//原商户订单号 需要回查的原交易商户订单号
            requestData.put("tranType","4");//
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }

        //3010
        @ParameterizedTest
        @DisplayName("退款")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCrefund() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCrefund";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("tranType","2");//2-退款
            requestData.put("merchantOrderId",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
            requestData.put("oldMerchantOrderId","65a8db851e8f4db4838c2141d7302ef3");//原商户订单号 需要进行退款的原交易的商户订单号
            requestData.put("amount","99");
            requestData.put("secondChannelCode","");
            requestData.put("orderTime","20200922180606");
//            requestData.put("payOrderId","20201007119142");
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

        }


    //3013
    @ParameterizedTest
    @DisplayName("申请对账")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCrequestReconcil() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCrequestReconcil";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
        requestData.put("secondChannelCode","");

        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

    }

    //3022
    @ParameterizedTest
    @DisplayName("来账通知")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCpaymentNotice() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCpaymentNotice";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("tranType","2");//2-退款
        requestData.put("merchantOrderId",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
        requestData.put("oldMerchantOrderId","5acebd55965c48169f3ff65bffbef624");//原商户订单号 需要进行退款的原交易的商户订单号
        requestData.put("amount","100000");
        requestData.put("secondChannelCode","");
//            requestData.put("orderTime","20200807");
//            requestData.put("payOrderId","20201007119142");
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());

    }

    //3023
    @ParameterizedTest
    @DisplayName("打印凭证")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCvoucherPrint() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCvoucherPrint";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("tranType","1");//
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
        requestData.put("oldMerchantOrderId","f679db32188244dca391fc888cfaaed5");//原商户订单号 需要进行退款的原交易的商户订单号
//        requestData.put("secondChannelCode","12");
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


//        //5001
//        @ParameterizedTest
//        @DisplayName("获取短信验证码")
//        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
//        public void ZHNLSCsendSMS() {
//            JSONObject requestData = new JSONObject();
//            String path = "/sdkapi/jdplat/ZHNLSCsendSMS";
//            requestData.put("trantype","0");//0-注册1-消费3-充值4-提现 5-冻结  6-解冻
//            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//
//            requestData.put("telNo","18711082300");//支付账号
//            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
//        }

        @ParameterizedTest
        @DisplayName("查询行名行号")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void ZHNLSCqueryBank() {
            JSONObject requestData = new JSONObject();
            String path = "/sdkapi/jdplat/ZHNLSCqueryBank";
            requestData.put("channelCode","AKKJ00000");//平台编码
            requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//
//            requestData.put("bankId","");//
            requestData.put("bankName","");//
            requestData.put("pageNum","1");//
            requestData.put("pageSize","9999");//
            ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
        }

    //3035
    @ParameterizedTest
    @DisplayName("批量开户通知")

    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCbatchopen() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCbatchOpen";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//
        requestData.put("tranType","per");//个人-per；企业-cor
        requestData.put("batchNo","99299");//
        requestData.put("fileType","0");//
        requestData.put("filePath","/ASP/FZJ0000001/REGISTER/20210105/");//
        requestData.put("detailFileName","FZJ0000001_99299.txt");//
        requestData.put("fileName","FZJ0000001_99299.zip");//

        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


    //3036
    @ParameterizedTest
    @DisplayName("批量开户回查")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCbatchopenQuery() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCbatchOpenQuery";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("secondChannelCode","");//二级平台编码
        requestData.put("tranType","per");//个人-per；企业-cor
        requestData.put("oldBatchNo","95089");//原交易批次号
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


    //3033
    @ParameterizedTest
    @DisplayName("查询交易汇总")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCqueryTotal() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCqueryTotal";
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("tranType","");//传控:全部；1-消费；2-退款；3-充值；4-提现；5-冻结；6-解冻；7-奖金发放；8-分账
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }

    @ParameterizedTest
    @DisplayName("对账文件查询")
    @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
    public void ZHNLSCqueryFile() {
        JSONObject requestData = new JSONObject();
        String path = "/sdkapi/jdplat/ZHNLSCqueryFile";
        requestData.put("channelCode","AKKJ00000");//平台编码
        requestData.put("tranSeqNum",UUID.randomUUID().toString().replace("-",""));//平台侧唯一标识一笔交易（等同于平台侧流水号
        requestData.put("oldTranSeqNum","122adcb1bc364abf86538a4e741827fd");//原商户订单号 需要进行退款的原交易的商户订单号
        requestData.put("secondChannelCode","");
        ExecuteRequest.executeRequest(requestData.toJSONString(), path,new JDEncryptAndDecrypt());
    }


        @ParameterizedTest
        @DisplayName("账户能力输出影像上传")
        @CsvFileSource(resources = "/Params.ZHNLSC/openPerAcc.csv", numLinesToSkip = 1)
        public void AcctOutputImgUploadService() {
            String path = "/sdkapi/jdplat/AcctOutputImgUploadService";
            JSONObject requestData = new JSONObject();

//            requestData.put("openId", "152200034");
//            requestData.put("electronicCardNo", "130562198810107080");
            requestData.put("ChannelNo", "0040002");
//            requestData.put("customerName", "张可爱");

            //requestData.put("channelNo ", "0080034");
//            requestData.put("Channeld ", "0040002");
            requestData.put("Flownum", "IOP20200824152261");
//            requestData.put("imgId", "1522062");
            requestData.put("imgName", "伊丽莎白的头像.jpg");
//            requestData.put("IdentCrdPht", getFileString("D:\\Program Files\\nginx-1.12.2\\nginx-1.12.2\\html\\张可爱的营业执照-91370921MA3N29UW1Q.bmp"));
            requestData.put("IdentCrdPht", getFileString("D:\\Program Files\\nginx-1.12.2\\nginx-1.12.2\\html\\ylsb-zheng.jpg"));
            ExecuteRequest.executeRequest(requestData.toJSONString(), path, new JDEncryptAndDecrypt());
            //伊丽莎白  33010019900530156X
//http://10.7.1.88:8080/image/20210316/0040002-OPENAPI001-20210316112943-035460/image/伊丽莎白身份证正面.jpg
//http://10.7.1.88:8080/image/20210316/0040002-OPENAPI001-20210316113135-035504/image/伊丽莎白身份证反面.jpg
            //张可爱 130562198810107080 91370921MA3N29UW1Q
//http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100215-046669/image/张可爱身份证正面.jpg
//http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318100400-046671/image/张可爱身份证背面.jpg
//http://10.7.1.88:8080/image/20210318/0040002-OPENAPI001-20210318113122-047118/image/张可爱的营业执照.jpg

    }

}
