package com.tvajjala.address.client.model.response;

public class CityStateRes {


    private String city;

    private String state;

    private Integer zipCode;

    private String county;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }


    @Override
    public String toString() {
        return "CityStateRes{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode=" + zipCode +
                ", county='" + county + '\'' +
                '}';
    }
}
