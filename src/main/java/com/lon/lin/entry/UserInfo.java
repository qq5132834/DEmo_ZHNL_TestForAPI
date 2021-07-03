package com.lon.lin.entry;

public class UserInfo {
    private String id;
    private String username;
    private String identNo;
    private String phone;
    private String bankCard;
    private String acctNo;
    private  String cstNo;

    public UserInfo(String username, String identNo, String phone, String bankCard, String acctNo, String cstNo) {
        this.username = username;
        this.identNo = identNo;
        this.phone = phone;
        this.bankCard = bankCard;
        this.acctNo = acctNo;
        this.cstNo = cstNo;
    }

    public UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentNo() {
        return identNo;
    }

    public void setIdentNo(String identNo) {
        this.identNo = identNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCstNo() {
        return cstNo;
    }

    public void setCstNo(String cstNo) {
        this.cstNo = cstNo;
    }
}
