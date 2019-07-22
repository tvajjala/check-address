package com.tvajjala.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This simple soap client will demonstrates the usage of parallel programming with
 * RxJava.
 * This client will accepts zipCode as query parameter and invokes cityState , alternateCities services
 * webServices in parallel and merges the result.
 *
 * @author ThirupathiReddy Vajjala
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args)
    {
        LOGGER.info("Application started...");
    }
}

