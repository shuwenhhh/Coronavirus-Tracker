package com.jianhui.CoronaVirustracker.controller;

import com.jianhui.CoronaVirustracker.models.CountryModel;
import com.jianhui.CoronaVirustracker.models.StatesModel;
import com.jianhui.CoronaVirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/totalCases")
    public Map<String,Integer> getTotalCases(){
        return coronaVirusDataService.getTotalCases();
    }

    @GetMapping("/{name}")
    public StatesModel getStateInfoByName(@PathVariable("name") String stateName){
        return coronaVirusDataService.getStateInfoByName(stateName);
    }

}
