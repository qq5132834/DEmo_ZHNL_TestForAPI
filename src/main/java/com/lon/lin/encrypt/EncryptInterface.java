package com.lon.lin.encrypt;

import com.alibaba.fastjson.JSONObject;

public interface EncryptInterface {

    String encryptData(Object requestData);
    String decryptData(Object responeseData);
}
