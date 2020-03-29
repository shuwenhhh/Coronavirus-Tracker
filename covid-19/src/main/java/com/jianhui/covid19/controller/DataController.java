package com.jianhui.covid19.controller;


import com.jianhui.covid19.models.CountriesModel;
import com.jianhui.covid19.models.StatesModel;
import com.jianhui.covid19.models.WorldModel;
import com.jianhui.covid19.services.DataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
public class DataController {

    @Autowired
    DataServices dataServices;

    @RequestMapping(path="/allCountries", method = RequestMethod.GET)
    @ResponseBody
    public List<CountriesModel> getAllCountries(){
        return dataServices.getAllCountries();
    }

    @RequestMapping(path="/allStates", method = RequestMethod.GET)
    @ResponseBody
    public List<StatesModel> getAllStates(){
        return dataServices.getAllStates();
    }

    @RequestMapping(path="/world", method = RequestMethod.GET)
    @ResponseBody
    public WorldModel getWorld(){
        return dataServices.getWorld();
    }

    @RequestMapping(path="country/state/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<StatesModel> getStateInfoByName(@PathVariable("name") String stateName){
        StatesModel state = dataServices.getStateInfoByName(stateName);
        return new ResponseEntity<>(state, state == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @RequestMapping(path="country/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CountriesModel> getCountryInfoByName(@PathVariable("name") String countryName){
        CountriesModel country = dataServices.getCountryInfoByName(countryName);
        return new ResponseEntity<>(country, country == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @RequestMapping(path="states/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StatesModel>> getStateListByCountry(@PathVariable("name") String name){
        List<StatesModel> list = dataServices.getStateListByCountry(name);
        return new ResponseEntity<>(list,list==null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
}
