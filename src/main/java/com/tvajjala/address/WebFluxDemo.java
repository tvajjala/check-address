package com.tvajjala.address;

//import reactor.core.publisher.Flux;
//import rx.schedulers.Schedulers;
//import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class WebFluxDemo {

    public static void main(String[] args) {


        Instant instant = Instant.now(); //can be LocalDateTime
        ZoneId systemZone = ZoneId.systemDefault(); // my timezone
        System.out.println(systemZone);

        ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);

        String date = "2018-12-01";

        System.out.println(currentOffsetForMyZone);
        System.out.println(LocalDate.parse(date));
        System.out.println(LocalDate.parse(date).atStartOfDay()
                .toInstant(currentOffsetForMyZone));
        System.out.println(Instant.now());

    }
}
