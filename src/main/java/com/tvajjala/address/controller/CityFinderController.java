package com.tvajjala.address.controller;

import com.tvajjala.address.model.CityFinder;
import com.tvajjala.address.service.CityFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Finds city based on the zipCode provided and also given alternate cities
 *
 * @author ThirupathiReddy
 */
@RestController
@RequestMapping("/find-city")
public class CityFinderController {


    @Autowired
    private CityFinderService cityFinderService;


    @RequestMapping
    public ResponseEntity<CityFinder> findCityByZipCode(@RequestParam(value = "zip-code") Integer zipCode) {

        return ResponseEntity.ok(cityFinderService.findCityName(zipCode));
    }
}
