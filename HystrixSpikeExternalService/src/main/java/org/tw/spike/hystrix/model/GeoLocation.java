package org.tw.spike.hystrix.model;

public class GeoLocation {

    private Float latitude;
    private Float longitude;

    public GeoLocation(Float latitude, Float longitude) {
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