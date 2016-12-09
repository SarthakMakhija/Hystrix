package org.tw.spike.hystrix.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tw.spike.hystrix.model.FlightWeatherCenter;
import org.tw.spike.hystrix.service.HystrixCoreService;

@RestController
public class HystrixCoreController {

    @Autowired
    private HystrixCoreService hystrixCoreService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public FlightWeatherCenter getFlightWeatherCenterInformation(){
        return hystrixCoreService.getFlightWeatherCenterInformation();
    }
}