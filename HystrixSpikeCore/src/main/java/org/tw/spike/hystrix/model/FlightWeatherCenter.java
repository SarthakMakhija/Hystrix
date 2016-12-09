package org.tw.spike.hystrix.model;


import fj.data.Either;
import org.tw.spike.hystrix.exception.HttpStatusCodeWrapperException;

public class FlightWeatherCenter {

    private GeoLocation geoLocation;
    private Weather     weather;

    public static FlightWeatherCenter make(Either<HttpStatusCodeWrapperException, GeoLocation> eitherOfLocation,
                                           Either<HttpStatusCodeWrapperException, Weather> eitherOfWeather){

        return new FlightWeatherCenter(eitherOfLocation, eitherOfWeather);
    }

    private FlightWeatherCenter(Either<HttpStatusCodeWrapperException, GeoLocation> eitherOfLocation,
                               Either<HttpStatusCodeWrapperException, Weather> eitherOfWeather) {

        geoLocation = eitherOfLocation.isLeft() ? GeoLocation.ZERO_CORDINATE_LOCATION : eitherOfLocation.right().value();
        weather     = eitherOfWeather.isLeft()  ? Weather.ZERO_DEGREE_WEATHER : eitherOfWeather.right().value();
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public Weather getWeather() {
        return weather;
    }
}