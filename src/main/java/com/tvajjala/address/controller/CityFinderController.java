package com.tvajjala.address.controller;

import com.tvajjala.address.model.CityFinder;
import com.tvajjala.address.service.CityFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Finds city based on the zipCode provided and also given alternate cities
 *
 * @author ThirupathiReddy
 */
@RestController

public class CityFinderController {


    private static final Logger LOGGER = LoggerFactory.getLogger(CityFinderController.class);


    @Autowired
    private CityFinderService cityFinderService;


    @GetMapping("/find-city")
    public ResponseEntity<CityFinder> findCityByZipCode(@RequestParam(value = "zip-code") Integer zipCode) {

        LOGGER.info("Processing request to find city with zipCode {} ", zipCode);
        return ResponseEntity.ok(cityFinderService.findCityName(zipCode));
    }


    @GetMapping("/completable")
    public String completableCityFinder() throws Exception{
        LOGGER.info(" Processing request  ");
        return cityFinderService.findName();
    }



}
