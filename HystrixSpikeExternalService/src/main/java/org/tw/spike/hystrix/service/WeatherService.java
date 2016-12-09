package org.tw.spike.hystrix.service;

import org.springframework.stereotype.Service;
import org.tw.spike.hystrix.exception.ServiceUnavailableException;
import org.tw.spike.hystrix.model.Weather;

import java.util.concurrent.atomic.AtomicInteger;

import static org.tw.spike.hystrix.utils.DelaySimulator.sleep;

@Service
public class WeatherService {

    private static final int SLEEP_INTERVAL_SECONDS = 2;

    private static AtomicInteger requestCounter = new AtomicInteger(0);

    public Weather getCurrentWeather(){
        simulateDelay();
        failIfOddRequestCounter();
        return new Weather(22.23f);
    }

    private void simulateDelay(){
        sleep(SLEEP_INTERVAL_SECONDS);
    }

    private void failIfOddRequestCounter(){
        int counter = requestCounter.incrementAndGet();
        if ( counter % 2 != 0) throw new ServiceUnavailableException("Error, generated for simulation");
    }
}