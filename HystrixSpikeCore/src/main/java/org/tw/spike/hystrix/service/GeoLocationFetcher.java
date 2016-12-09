package org.tw.spike.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import fj.data.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;
import org.tw.spike.hystrix.exception.wrapper.ExceptionWrapper;
import org.tw.spike.hystrix.model.GeoLocation;
import org.tw.spike.hystrix.statuscode.translator.HttpStatusCodeTranslator;

import java.util.concurrent.Future;

@Service
@RefreshScope
public class GeoLocationFetcher {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpStatusCodeTranslator httpStatusCodeTranslator;

    @Autowired
    private ExceptionWrapper exceptionWrapper;

    private static final String LOCATION_SERVICE_URI = "http://localhost:8181/geolocation";

    @HystrixCommand(fallbackMethod = "locationFallback",
            commandKey = "locationKey",
            groupKey   = "booking"
            //,
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
//            }
    )
    public Future<Either<HttpStatusCodeWrapperException, GeoLocation>> getLocation(){
        return new AsyncResult<Either<HttpStatusCodeWrapperException, GeoLocation>>(){
            @Override
            public Either<HttpStatusCodeWrapperException, GeoLocation> invoke() {
                try{
                    return
                            httpStatusCodeTranslator.translateSuccessStatusCodeTo(restTemplate.getForObject(LOCATION_SERVICE_URI,
                                                                                                            GeoLocation.class));
                }
                catch (HttpStatusCodeException e){
                    throw new HttpStatusCodeWrapperException(e, e.getStatusCode());
                }
            }
        };
    }

    @HystrixCommand
    public Either<HttpStatusCodeWrapperException, GeoLocation> locationFallback(Throwable e) {
        HttpStatusCodeWrapperException wrapperException = exceptionWrapper.wrapException(e);
        return httpStatusCodeTranslator.translateFailureStatusCodeTo(wrapperException, GeoLocation.ZERO_CORDINATE_LOCATION);
    }
}
