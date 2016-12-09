package org.tw.spike.hystrix.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Weather implements Serializable{

    public static final Weather ZERO_DEGREE_WEATHER = new Weather(0f);

    private Float temperature;

    @JsonCreator
    public Weather(@JsonProperty("temperature") Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperature(){
        return temperature;
    }
}
