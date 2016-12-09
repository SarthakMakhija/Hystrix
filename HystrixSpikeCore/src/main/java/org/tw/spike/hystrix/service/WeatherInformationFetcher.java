package org.tw.spike.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import fj.data.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;
import org.tw.spike.hystrix.exception.wrapper.ExceptionWrapper;
import org.tw.spike.hystrix.model.GeoLocation;
import org.tw.spike.hystrix.model.Weather;
import org.tw.spike.hystrix.statuscode.translator.HttpStatusCodeTranslator;

import java.util.concurrent.Future;

@Service
public class WeatherInformationFetcher {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpStatusCodeTranslator httpStatusCodeTranslator;

    @Autowired
    private ExceptionWrapper exceptionWrapper;

    private static final String WEATHER_SERVICE_URI = "http://localhost:8181/weather";

    @HystrixCommand(fallbackMethod = "weatherFallback",
            commandKey = "weatherKey",
            groupKey   = "booking",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
            }
    )
    public Future<Either<HttpStatusCodeWrapperException, Weather>> getCurrentWeather(){
        return new AsyncResult<Either<HttpStatusCodeWrapperException, Weather>>(){
            @Override
            public Either<HttpStatusCodeWrapperException, Weather> invoke() {
                try{
                    return
                            httpStatusCodeTranslator.translateSuccessStatusCodeTo(restTemplate.getForObject(WEATHER_SERVICE_URI,
                                    Weather.class));
                }
                catch (HttpStatusCodeException e){
                    throw new HttpStatusCodeWrapperException(e, e.getStatusCode());
                }
            }
        };
    }

    @HystrixCommand
    public Either<HttpStatusCodeWrapperException, Weather> weatherFallback(Throwable t) {
        HttpStatusCodeWrapperException wrapperException = exceptionWrapper.wrapException(t);
        return httpStatusCodeTranslator.translateFailureStatusCodeTo(wrapperException, Weather.ZERO_DEGREE_WEATHER);
    }
}
