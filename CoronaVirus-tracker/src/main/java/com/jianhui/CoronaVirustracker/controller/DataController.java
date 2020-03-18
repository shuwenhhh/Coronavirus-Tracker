package com.jianhui.CoronaVirustracker.controller;

import com.jianhui.CoronaVirustracker.models.CountryModel;
import com.jianhui.CoronaVirustracker.models.StatesModel;
import com.jianhui.CoronaVirustracker.models.WorldModel;
import com.jianhui.CoronaVirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/allStates")
    public List<StatesModel> getAllStates(){
        return coronaVirusDataService.getAllStates();
    }

    @GetMapping("/allCountries")
    public List<CountryModel> getAllCountries(){
        return coronaVirusDataService.getAllCountries();
    }

    @GetMapping("/world")
    public WorldModel getWorld() throws ParseException {
        return coronaVirusDataService.getWorld();
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<StatesModel> getStateInfoByName(@PathVariable("name") String stateName){
        StatesModel state = coronaVirusDataService.getStateInfoByName(stateName);
        return new ResponseEntity<>(state, state == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @GetMapping("country/{name}")
    public ResponseEntity<CountryModel> getCountryInfoByName(@PathVariable("name") String countryName){
        CountryModel country = coronaVirusDataService.getCountryInfoByName(countryName);
        return new ResponseEntity<>(country, country == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @GetMapping("states/{name}")
    public ResponseEntity<List<StatesModel>> getStateListByCountry(@PathVariable("name") String name){
        List<StatesModel> list = coronaVirusDataService.getStateListByCountry(name);
        return new ResponseEntity<>(list,list==null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

}
