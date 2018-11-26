package com.tvajjala.address.soap.model.request;

public class CityStateReq {

    private Integer zipCode;

    public CityStateReq() {

    }

    public CityStateReq(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
