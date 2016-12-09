package org.tw.spike.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tw.spike.hystrix.model.Weather;
import org.tw.spike.hystrix.service.WeatherService;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    @ResponseBody
    public Weather getCurrentWeather(){
        return weatherService.getCurrentWeather();
    }
}