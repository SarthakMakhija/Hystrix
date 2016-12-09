package org.tw.spike.hystrix.service;

import fj.data.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;
import org.tw.spike.hystrix.model.FlightWeatherCenter;
import org.tw.spike.hystrix.model.GeoLocation;
import org.tw.spike.hystrix.model.Weather;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class HystrixCoreService {

    @Autowired
    private GeoLocationFetcher geoLocationFetcher;

    @Autowired
    private WeatherInformationFetcher weatherInformationFetcher;

    public FlightWeatherCenter getFlightWeatherCenterInformation(){
        Future<Either<HttpStatusCodeWrapperException, GeoLocation>> futureGeoLocation =  geoLocationFetcher.getLocation();
        Future<Either<HttpStatusCodeWrapperException, Weather>>     futureWeather     =  weatherInformationFetcher.getCurrentWeather();

        return constructFlightWeatherCenter( extractGeoLocation (futureGeoLocation), extractWeather (futureWeather) );
    }

    private Either<HttpStatusCodeWrapperException, GeoLocation> extractGeoLocation(Future<Either<HttpStatusCodeWrapperException, GeoLocation>> future ) {
        return extractFuture(future);
    }

    private Either<HttpStatusCodeWrapperException, Weather> extractWeather(Future<Either<HttpStatusCodeWrapperException, Weather>> future ) {
         return extractFuture (future);
    }

    private <A extends  HttpStatusCodeWrapperException, B extends Serializable> Either<A, B> extractFuture (Future<Either<A, B>> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            return Either.left( null );
        } catch (ExecutionException e) {
            return Either.left( null );
        }
    }

    private FlightWeatherCenter constructFlightWeatherCenter(Either<HttpStatusCodeWrapperException, GeoLocation> eitherGeoLocation,
                                              Either<HttpStatusCodeWrapperException, Weather>     eitherWeather){
        return FlightWeatherCenter.make(eitherGeoLocation, eitherWeather);
    }
}