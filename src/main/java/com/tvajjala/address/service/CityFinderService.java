package com.tvajjala.address.service;

import com.tvajjala.address.client.model.response.AlternateCities;
import com.tvajjala.address.client.model.response.CityStateRes;
import com.tvajjala.address.client.soap.AlternateCitiesClient;
import com.tvajjala.address.client.soap.CityClient;
import com.tvajjala.address.model.CityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

@Service
public class CityFinderService {

    @Autowired
    private CityClient cityClient;

    @Autowired
    private AlternateCitiesClient alternateCitiesClient;

    public CityFinder findCityName(Integer zipCode) {

        Single<CityStateRes> csRes = cityClient.findCityNameAsync(zipCode);
        Single<AlternateCities> csRes2 = alternateCitiesClient.findCityNameAsync(zipCode);

        return Single.zip(csRes, csRes2, (res, res2) ->
                {
                    CityFinder cityFinder = new CityFinder();
                    cityFinder.setCityStateRes(res);
                    cityFinder.setAlternateCities(res2);
                    return cityFinder;
                }
        ).subscribeOn(Schedulers.io()).toBlocking().value();

    }


}
