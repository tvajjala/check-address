package com.tvajjala.address.model;

import com.tvajjala.address.client.model.response.AlternateCities;
import com.tvajjala.address.client.model.response.CityStateRes;

public class CityFinder {


    private CityStateRes cityStateRes;

    private AlternateCities alternateCities;


    public CityStateRes getCityStateRes() {
        return cityStateRes;
    }

    public void setCityStateRes(CityStateRes cityStateRes) {
        this.cityStateRes = cityStateRes;
    }

    public AlternateCities getAlternateCities() {
        return alternateCities;
    }

    public void setAlternateCities(AlternateCities alternateCities) {
        this.alternateCities = alternateCities;
    }

}
