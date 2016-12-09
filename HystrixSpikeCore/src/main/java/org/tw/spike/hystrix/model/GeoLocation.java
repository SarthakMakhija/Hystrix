package org.tw.spike.hystrix.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GeoLocation implements Serializable{

    private Float latitude;
    private Float longitude;

    public static final GeoLocation ZERO_CORDINATE_LOCATION = new GeoLocation(0f, 0f);

    @JsonCreator
    public GeoLocation(@JsonProperty("latitude") Float latitude, @JsonProperty("longitude")  Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
}