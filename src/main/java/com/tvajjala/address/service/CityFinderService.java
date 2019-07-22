package com.tvajjala.address.service;

import com.tvajjala.address.client.model.response.AlternateCities;
import com.tvajjala.address.client.model.response.CityStateRes;
import com.tvajjala.address.client.soap.AlternateCitiesClient;
import com.tvajjala.address.client.soap.CityClient;
import com.tvajjala.address.model.CityFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This service will make two parallel calls to two different soap services and aggregate result back.
 *
 * @author ThirupathiReddy Vajjala
 */
@Service
public class CityFinderService {



    private static final Logger LOGGER = LoggerFactory.getLogger(CityFinderService.class);

    private CityClient cityClient;

    private AlternateCitiesClient alternateCitiesClient;

    @Autowired
    public CityFinderService(CityClient cityClient, AlternateCitiesClient alternateCitiesClient){
        this.cityClient=cityClient;
        this.alternateCitiesClient=alternateCitiesClient;
    }

    public CityFinder findCityName(Integer zipCode) {

        LOGGER.info("Searching city with zipcode {}", zipCode);
        Single<CityStateRes> csRes = cityClient.findCityNameAsync(zipCode).subscribeOn(Schedulers.io());
        Single<AlternateCities> csRes2 = alternateCitiesClient.findAlternateCitiesAsync(zipCode).subscribeOn(Schedulers.io());

        CityFinder cityFinderResponse= Single.zip(csRes, csRes2, (res, res2) ->
                {
                    CityFinder cityFinder = new CityFinder();
                    cityFinder.setCityStateRes(res);
                    cityFinder.setAlternateCities(res2);
                    return cityFinder;
                }
        ).subscribeOn(Schedulers.io()).toBlocking().value();

        LOGGER.info("Returning response {}", cityFinderResponse);
        return cityFinderResponse;
    }



    public String findName() throws Exception{


        LOGGER.info("Finding name");
        CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            LOGGER.info("First stage");
            return "Rajeev";
        }).thenApply(name -> {
            LOGGER.info("Second stage");
            return "Hello " + name;
        }).thenApply(greeting -> {
            LOGGER.info("Third stage");
            return greeting + ", Welcome to the CalliCoder Blog";
        });


        LOGGER.info("Returning  name");
       return welcomeText.get();
    }

}
