package com.tvajjala.address.service;

import com.tvajjala.address.soap.model.request.CityStateReq;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class CityFinderService {


    static final String URI = "http://ws.cdyne.com/psaddress/addresslookup.asmx";
    @Autowired
    WebServiceTemplate webServiceTemplate;


    public CityStateRes findCityName(Integer zipCode) {

        return (CityStateRes) webServiceTemplate.marshalSendAndReceive(URI, new CityStateReq(zipCode));

    }


}
