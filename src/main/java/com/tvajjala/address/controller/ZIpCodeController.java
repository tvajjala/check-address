package com.tvajjala.address.controller;

import com.tvajjala.address.service.CityStateService;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is just pilot implementation for moxy soapClient.
 *
 * @author ThirupathiReddy
 */
@RestController
@RequestMapping("/zip")
public class ZIpCodeController {


    @Autowired
    private CityStateService cityStateService;


    @RequestMapping("/{zip-code}")
    public ResponseEntity<List<CityStateRes>> findCityByZipCode(@PathVariable(name = "zip-code") Integer zipCode) {
        return ResponseEntity.ok(cityStateService.findCityName(zipCode));
    }
}
