package com.tvajjala.address.model;

import com.tvajjala.address.client.model.response.AlternateCities;
import com.tvajjala.address.client.model.response.CityStateRes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CityFinder {


    Instant instant = Instant.now();


    ZonedDateTime zonedDateTime = ZonedDateTime.now();


    LocalDate localDate = LocalDate.now();

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Instant getInstant2() {
        return LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

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
