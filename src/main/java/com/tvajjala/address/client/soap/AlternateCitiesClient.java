package com.tvajjala.address.client.soap;

import com.tvajjala.address.client.model.request.CityStateReq;
import com.tvajjala.address.client.model.response.AlternateCities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import rx.Single;

@Component
public class AlternateCitiesClient {


    @Autowired
    WebServiceTemplate alternateCityTemplate;


    static final Logger LOGGER = LoggerFactory.getLogger(AlternateCitiesClient.class);

    static final String URI = "http://ws.cdyne.com/psaddress/addresslookup.asmx";

    static final String SOAP_ACTION = "http://ws.cdyne.com/AlternateCities";


    public Single<AlternateCities> findAlternateCitiesAsync(Integer zipCode) {

        return Single.create(singleSubscriber -> {
            AlternateCities alternateCities = (AlternateCities) alternateCityTemplate.marshalSendAndReceive(URI, new CityStateReq(zipCode),
                    message -> {
                        LOGGER.info("message {} ", message);
                        SoapMessage soapMessage = (SoapMessage) message;
                        SoapHeader soapHeader = soapMessage.getSoapHeader();
                        soapHeader.examineAllHeaderElements().forEachRemaining(e -> LOGGER.error(e.getText()));
                        soapMessage.setSoapAction(SOAP_ACTION);
                        LOGGER.info("soapAction  {} ", soapMessage.getSoapAction());
                    });
            singleSubscriber.onSuccess(alternateCities);
        });


    }


}
