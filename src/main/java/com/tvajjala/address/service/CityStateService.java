package com.tvajjala.address.service;

import com.tvajjala.address.soap.client.CityStateClient;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CityStateService {

    @Autowired
    private CityStateClient cityStateClient;

    public List<CityStateRes> findCityName(Integer zipCode) {

        Single<CityStateRes> csRes = cityStateClient.findCityNameAsync(zipCode);
        Single<CityStateRes> csRes2 = cityStateClient.findCityNameAsync(zipCode);

        return Single.zip(csRes, csRes2, (res, res2) -> Stream.of(res, res2).collect(Collectors.toList()))
                .subscribeOn(Schedulers.io()).toBlocking().value();

    }


}
