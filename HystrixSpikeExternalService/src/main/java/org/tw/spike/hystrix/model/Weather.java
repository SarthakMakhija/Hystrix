package org.tw.spike.hystrix.model;

public class Weather {

    private Float temperature;

    public Weather(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperature(){
        return temperature;
    }
}
