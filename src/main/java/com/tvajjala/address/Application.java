package com.tvajjala.address;

import com.tvajjala.address.service.CityFinderService;
import com.tvajjala.address.soap.model.response.CityStateRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ThirupathiReddy Vajjala
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Autowired
    CityFinderService cityFinderService;

    @Override
    public void run(String... args) {

        CityStateRes cityStateRes = cityFinderService.findCityName(15220);

        System.out.println(cityStateRes);
    }
}
