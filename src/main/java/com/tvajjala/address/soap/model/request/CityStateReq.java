package com.tvajjala.address.soap.model.request;

public class CityStateReq {

    private Integer zipCode;

    private String licenseKey;

    public CityStateReq() {

    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public CityStateReq(Integer zipCode) {

        this.zipCode = zipCode;
        this.licenseKey="?";
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
