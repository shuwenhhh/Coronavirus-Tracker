package com.jianhui.CoronaVirustracker.services;


import com.jianhui.CoronaVirustracker.models.CountryModel;
import com.jianhui.CoronaVirustracker.models.StatesModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CoronaVirusDataService {

    private static final String CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private static final String RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
    private static final String DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
    private List<StatesModel> allStates = new ArrayList<>();
    private List<CountryModel> allCountries = new ArrayList<>();
    private Set<String> countrySet = new HashSet<>();
    private Map<String,Integer> totalCases = new HashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");


    @PostConstruct
    @Scheduled(cron = "* 30 * * * *")
    public void fetchCoronaVirusData() throws IOException, InterruptedException, ParseException {
        List<StatesModel> newStates = new ArrayList<>();
        List<CountryModel> newCountries = new ArrayList<>();
        HttpClient client = HttpClient.newBuilder().build();
        String[] urls = new String[]{CONFIRMED_URL, RECOVERED_URL, DEATH_URL};
        for (int i=0; i<3; ++i){
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urls[i])).build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            StringReader csvBodyReader = new StringReader(response.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
            if (i == 0)
                fetchConfirmedData(records,newStates);
            else if (i == 1)
                fetchRecoveredData(records,newStates);
            else
                fetchDeathData(records,newStates);
        }
        fetchCountryData(newStates, newCountries);
        allStates = newStates;
        allCountries = newCountries;

    }

    private void fetchConfirmedData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException {
        int totalConfirmed=0;
        for (CSVRecord record : records){
            StatesModel tempState = new StatesModel();
            tempState.setState(record.get("Province/State"));
            tempState.setCountry(record.get("Country/Region"));
            tempState.setLatitude(record.get("Lat"));
            tempState.setLongitude(record.get("Long"));
            Map<String,Integer> map = new HashMap<>();
            getStateDailyReport(map,record);
            tempState.setDailyConfirmed(map);
            newStates.add(tempState);
            this.countrySet.add(record.get("Country/Region"));
            totalConfirmed += Integer.parseInt(record.get(record.size()-1));
        }
        totalCases.put("totalConfirmedCase",totalConfirmed);
    }

    private void fetchRecoveredData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException{
        int index = 0;
        int totalRecovered = 0;
        for (CSVRecord record : records){
            Map<String,Integer> map = new HashMap<>();
            getStateDailyReport(map,record);
            newStates.get(index++).setDailyRecovered(map);
            totalRecovered += Integer.parseInt(record.get(record.size()-1));
        }
        totalCases.put("totalRecoveredCase",totalRecovered);
    }

    private void fetchDeathData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException{
        int index = 0;
        int totalDeath = 0;
        for (CSVRecord record : records){
            Map<String,Integer> map = new HashMap<>();
            getStateDailyReport(map,record);
            newStates.get(index++).setDailyDeath(map);
            totalDeath += Integer.parseInt(record.get(record.size()-1));
        }
        totalCases.put("totalDeathCase",totalDeath);
    }

    private void fetchCountryData(List<StatesModel> newStates, List<CountryModel> newCountries){
        for(String country: this.countrySet){
            CountryModel newCountry = new CountryModel();
            newCountry.setCountryName(country);
            List<Map<String,Integer>> confirmedDailyList = new LinkedList<>();
            List<Map<String,Integer>> recoveredDailyList = new LinkedList<>();
            List<Map<String,Integer>> deathDailyList = new LinkedList<>();
            for (StatesModel state: newStates){
                if (state.getCountry().equalsIgnoreCase(country)){
                    confirmedDailyList.add(state.getDailyConfirmed());
                    recoveredDailyList.add(state.getDailyRecovered());
                    deathDailyList.add(state.getDailyDeath());
                }
            }
            Map<String,Integer> confirmedDaily = new HashMap<>();
            Map<String,Integer> recoveredDaily = new HashMap<>();
            Map<String,Integer> deathDaily = new HashMap<>();
            getCountryDailyReport(confirmedDaily,confirmedDailyList);
            getCountryDailyReport(recoveredDaily,recoveredDailyList);
            getCountryDailyReport(deathDaily,deathDailyList);
            newCountry.setDailyConfirmed(confirmedDaily);
            newCountry.setDailyRecovered(recoveredDaily);
            newCountry.setDailyDeath(deathDaily);
            newCountries.add(newCountry);
        }
    }

    private void getCountryDailyReport (Map<String,Integer> map, List<Map<String,Integer>> mapList){
        for (Map<String,Integer> daily: mapList){
            for (String date: daily.keySet()){
                map.put(date,map.getOrDefault(date,0)+daily.get(date));
            }
        }

    }

    private void getStateDailyReport(Map<String,Integer> map, CSVRecord record) throws ParseException {
        String date = "1/22/20";
        int cases=0;
        String today = dateFormat.format(new Date());
        while (!date.equals(today)){
            cases = Integer.parseInt(record.get(date));
            map.put(date,cases);
            date = plusOneDay(date);
        }
    }

    private String plusOneDay(String dt) throws ParseException {
        Date date = dateFormat.parse(dt);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return dateFormat.format(c.getTime());
    }


    public StatesModel getStateInfoByName(String name){
        for (StatesModel state: allStates){
            if (state.getState().equalsIgnoreCase(name))
                return state;
        }
        return null;
    }

    public List<StatesModel> getAllStates(){
        return allStates;
    }

    public List<CountryModel> getAllCountries() {
        return allCountries;
    }

    public Map<String,Integer> getTotalCases(){
        return totalCases;
    }
}
