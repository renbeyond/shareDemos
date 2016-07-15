package com.example.demo.peng.retrofitwithokhttp.bean;

/**
 * Created by milo on 15/12/19.
 * 收货地址实体类
 */
public class Address {
    String city;
    String provinces;
    String consignee;
    String county;
    String detailedAddress;
    String mobileNum;
    boolean defaultChose;
    String _id;

    public Address(String city, String provinces, String consignee, String county, String detailedAddress, String mobileNum, boolean defaultChose, String _id) {
        this.city = city;
        this.provinces = provinces;
        this.consignee = consignee;
        this.county = county;
        this.detailedAddress = detailedAddress;
        this.mobileNum = mobileNum;
        this.defaultChose = defaultChose;
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public boolean isDefaultChose() {
        return defaultChose;
    }

    public void setDefaultChose(boolean defaultChose) {
        this.defaultChose = defaultChose;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
