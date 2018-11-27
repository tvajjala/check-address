package com.tvajjala.address.controller;

import com.tvajjala.address.service.CityStateService;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is just pilot implementation for moxy soapClient.
 *
 * @author ThirupathiReddy
 */
@RestController
@RequestMapping("/city-state")
public class CityStateController {


    @Autowired
    CityStateService cityStateService;


    @RequestMapping("/{zip-code}")
    public ResponseEntity<CityStateRes> findCityByZipCode(@PathVariable(name = "zip-code") Integer zipCode) {
        return ResponseEntity.ok(cityStateService.findCityName(zipCode));
    }
}
