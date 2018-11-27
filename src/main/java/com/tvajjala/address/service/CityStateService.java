package com.tvajjala.address.service;

import com.tvajjala.address.soap.model.request.CityStateReq;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

@Service
public class CityStateService {


    static final Logger LOGGER = LoggerFactory.getLogger(CityStateService.class);

    static final String URI = "http://ws.cdyne.com/psaddress/addresslookup.asmx";

    static final String SOAP_ACTION = "http://ws.cdyne.com/ReturnCityState";

    @Autowired
    WebServiceTemplate webServiceTemplate;


    public CityStateRes findCityName(Integer zipCode) {

        return (CityStateRes) webServiceTemplate.marshalSendAndReceive(URI, new CityStateReq(zipCode), message -> {
            LOGGER.info("message {} ", message);
            SoapMessage soapMessage = (SoapMessage) message;
            SoapHeader soapHeader = soapMessage.getSoapHeader();
            soapHeader.examineAllHeaderElements().forEachRemaining(e -> LOGGER.error(e.getText()));
            soapMessage.setSoapAction(SOAP_ACTION);
            LOGGER.info("soapAction  {} ", soapMessage.getSoapAction());
        });

    }


}
