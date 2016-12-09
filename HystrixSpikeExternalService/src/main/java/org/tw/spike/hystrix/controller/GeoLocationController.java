package org.tw.spike.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tw.spike.hystrix.model.GeoLocation;
import org.tw.spike.hystrix.service.GeoLocationService;

@RestController
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    @RequestMapping(value = "/geolocation", method = RequestMethod.GET)
    @ResponseBody
    public GeoLocation getCurrentLocation(){
        return geoLocationService.getCurrentLocation();
    }
}