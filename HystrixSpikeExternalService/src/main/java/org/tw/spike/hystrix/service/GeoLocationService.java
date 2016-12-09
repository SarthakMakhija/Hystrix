package org.tw.spike.hystrix.service;

import org.springframework.stereotype.Service;
import org.tw.spike.hystrix.exception.InternalServerException;
import org.tw.spike.hystrix.model.GeoLocation;

import java.util.concurrent.atomic.AtomicInteger;

import static org.tw.spike.hystrix.utils.DelaySimulator.sleep;

@Service
public class GeoLocationService {

    private static final int SLEEP_INTERVAL_SECONDS = 3;

    private static AtomicInteger requestCounter = new AtomicInteger(0);

    public GeoLocation getCurrentLocation() {
        simulateDelay();
        failIfEvenRequestCounter();
        return new GeoLocation(10.12f, 12.23f);
    }

    private void simulateDelay(){
        sleep(SLEEP_INTERVAL_SECONDS);
    }

    private void failIfEvenRequestCounter(){
        int counter = requestCounter.incrementAndGet();
        if ( counter % 2 == 0) throw new InternalServerException("Error, generated for simulation");
    }
}