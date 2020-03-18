package com.jianhui.CoronaVirustracker.services;


import com.jianhui.CoronaVirustracker.models.CountryModel;
import com.jianhui.CoronaVirustracker.models.StatesModel;
import com.jianhui.CoronaVirustracker.models.WorldModel;
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
    private WorldModel worldModel = new WorldModel();
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
        getWorldDailyReport();
        getStateDailyList();
        getCountryDailyList();
        getWorldDailyList();

    }

    private void fetchConfirmedData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException {
        int totalConfirmed=0;
        for (CSVRecord record : records){
            StatesModel tempState = new StatesModel();
            String country = record.get("Country/Region");
            String state = record.get("Province/State");
            if (state.length() != 0 && state.split(",").length <= 1)
                 tempState.setState(state);
            else
                tempState.setState("UNDEF");
            tempState.setCountry(country);
            this.countrySet.add(country);
            tempState.setLatitude(record.get("Lat"));
            tempState.setLongitude(record.get("Long"));

            Map<String,Integer> map = new HashMap<>();
            getStateDailyReport(map,record);
            tempState.setDailyConfirmed(map);
            if (record.get(record.size()-1) == null || record.get(record.size()-1).length() == 0)
                tempState.setTotalConfirmed(Integer.parseInt(record.get(record.size()-2)));
            else
                tempState.setTotalConfirmed(Integer.parseInt(record.get(record.size()-1)));

            newStates.add(tempState);

            totalConfirmed += tempState.getTotalConfirmed();
        }
        worldModel.setTotalConfirmed(totalConfirmed);
    }

    private void fetchRecoveredData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException{
        int index = 0;
        int totalRecovered = 0;
        for (CSVRecord record : records){
            Map<String,Integer> map = new HashMap<>();
            getStateDailyReport(map,record);
            newStates.get(index).setDailyRecovered(map);
            if (record.get(record.size()-1) == null || record.get(record.size()-1).length() == 0)
                newStates.get(index).setTotalRecovered(Integer.parseInt(record.get(record.size()-2)));
            else
                newStates.get(index).setTotalRecovered(Integer.parseInt(record.get(record.size()-1)));
            totalRecovered += newStates.get(index++).getTotalRecovered();
        }
        worldModel.setTotalRecovered(totalRecovered);
    }

    private void fetchDeathData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException{
        int index = 0;
        int totalDeath = 0;
        for (CSVRecord record : records){
            Map<String,Integer> map = new LinkedHashMap<>();
            getStateDailyReport(map,record);
            newStates.get(index).setDailyDeath(map);
            if (record.get(record.size()-1) == null || record.get(record.size()-1).length() == 0)
                newStates.get(index).setTotalDeath(Integer.parseInt(record.get(record.size()-2)));
            else
                newStates.get(index).setTotalDeath(Integer.parseInt(record.get(record.size()-1)));

            totalDeath += newStates.get(index++).getTotalDeath();
        }
        worldModel.setTotalDeath(totalDeath);
    }

    private void fetchCountryData(List<StatesModel> newStates, List<CountryModel> newCountries){
        for(String country: this.countrySet){
            List<StatesModel> list = new LinkedList<>();
            CountryModel newCountry = new CountryModel();
            newCountry.setCountryName(country);
            int countryTotalConfirmed=0;
            int countryTotalRecovered=0;
            int countryTotalDeath=0;
            List<Map<String,Integer>> confirmedDailyList = new LinkedList<>();
            List<Map<String,Integer>> recoveredDailyList = new LinkedList<>();
            List<Map<String,Integer>> deathDailyList = new LinkedList<>();
            for (StatesModel state: newStates){
                if (state.getCountry().equalsIgnoreCase(country)){
                    if (!state.getState().equalsIgnoreCase("UNDEF"))
                        list.add(state);
                    confirmedDailyList.add(state.getDailyConfirmed());
                    recoveredDailyList.add(state.getDailyRecovered());
                    deathDailyList.add(state.getDailyDeath());
                    countryTotalConfirmed += state.getTotalConfirmed();
                    countryTotalRecovered += state.getTotalRecovered();
                    countryTotalDeath += state.getTotalDeath();
                }
            }
            newCountry.setTotalConfirmed(countryTotalConfirmed);
            newCountry.setTotalRecovered(countryTotalRecovered);
            newCountry.setTotalDeath(countryTotalDeath);
            newCountry.setStates(list);
            if (country.equalsIgnoreCase("us")){
                for (StatesModel s: list)
                    System.out.println(s.getState());
            }

            Map<String,Integer> confirmedDaily = new LinkedHashMap<>();
            Map<String,Integer> recoveredDaily = new LinkedHashMap<>();
            Map<String,Integer> deathDaily = new LinkedHashMap<>();

            getCountryDailyReport(confirmedDaily,confirmedDailyList);
            getCountryDailyReport(recoveredDaily,recoveredDailyList);
            getCountryDailyReport(deathDaily,deathDailyList);

            newCountry.setDailyConfirmed(confirmedDaily);
            newCountry.setDailyRecovered(recoveredDaily);
            newCountry.setDailyDeath(deathDaily);

            newCountries.add(newCountry);
        }
    }

    private void getWorldDailyReport() throws ParseException {
        String date = "1/22/20";
        String today = dateFormat.format(new Date());
        Map<String,Integer> confirmedDaily = new LinkedHashMap<>();
        Map<String, Integer> recoveredDaily = new LinkedHashMap<>();
        Map<String, Integer> deathDaily = new LinkedHashMap<>();
        boolean isEnd = false;
        while (!date.equals(today)) {
            int confirmed=0, recovered=0,death=0;
            for (CountryModel country : allCountries) {
                if (!country.getDailyConfirmed().containsKey(date)){
                    isEnd = true;
                    break;
                }
                confirmed += country.getDailyConfirmed().get(date);
                recovered += country.getDailyRecovered().get(date);
                death += country.getDailyDeath().get(date);

            }
            if (isEnd)
                break;
            confirmedDaily.put(date, confirmed);
            recoveredDaily.put(date, recovered);
            deathDaily.put(date, death);

            date = plusOneDay(date);
        }
        worldModel.setDailyConfirmed(confirmedDaily);
        worldModel.setDailyDeath(deathDaily);
        worldModel.setDailyRecovered(recoveredDaily);
    }

    private void getCountryDailyReport (Map<String,Integer> map, List<Map<String,Integer>> mapList){
        for (Map<String,Integer> daily: mapList) {
            for (String date : daily.keySet()) {
                map.put(date, map.getOrDefault(date, 0) + daily.get(date));
            }
        }
    }

    private void getStateDailyReport(Map<String,Integer> map, CSVRecord record) throws ParseException {
        String date = "1/22/20";
        String today = dateFormat.format(new Date());
        while (!date.equals(today)){
            map.put(date,Integer.parseInt(record.get(date)));
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

    public void getWorldDailyList() throws ParseException {
        Map<String,Integer> dailyConfirmed = worldModel.getDailyConfirmed();
        Map<String,Integer> dailyRecovered = worldModel.getDailyRecovered();
        Map<String,Integer> dailyDeath = worldModel.getDailyDeath();
        List<Map<String,String>> list = new LinkedList<>();
        String date = "1/22/20";
        String today = dateFormat.format(new Date());
        while (!date.equals(today)){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("date",date);
            map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
            map.put("recovered", String.valueOf(dailyRecovered.get(date)));
            map.put("death", String.valueOf(dailyDeath.get(date)));
            list.add(map);
            date = plusOneDay(date);
        }
        worldModel.setDailyList(list);
    }

    public void getCountryDailyList() throws ParseException{
        for (CountryModel country: allCountries){
            Map<String,Integer> dailyConfirmed = country.getDailyConfirmed();
            Map<String,Integer> dailyRecovered = country.getDailyRecovered();
            Map<String,Integer> dailyDeath = country.getDailyDeath();
            List<Map<String,String>> list = new LinkedList<>();
            String date = "1/22/20";
            String today = dateFormat.format(new Date());
            while (!date.equals(today)){
                Map<String,String> map = new LinkedHashMap<>();
                map.put("date",date);
                map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
                map.put("recovered", String.valueOf(dailyRecovered.get(date)));
                map.put("death", String.valueOf(dailyDeath.get(date)));
                list.add(map);
                date = plusOneDay(date);
            }
            country.setDailyList(list);
        }
    }

    public void getStateDailyList() throws ParseException{
        for (StatesModel state: allStates){
            Map<String,Integer> dailyConfirmed = state.getDailyConfirmed();
            Map<String,Integer> dailyRecovered = state.getDailyRecovered();
            Map<String,Integer> dailyDeath = state.getDailyDeath();
            List<Map<String,String>> list = new LinkedList<>();
            String date = "1/22/20";
            String today = dateFormat.format(new Date());
            while (!date.equals(today)){
                Map<String,String> map = new LinkedHashMap<>();
                map.put("date",date);
                map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
                map.put("recovered", String.valueOf(dailyRecovered.get(date)));
                map.put("death", String.valueOf(dailyDeath.get(date)));
                list.add(map);
                date = plusOneDay(date);
            }
            state.setDailyList(list);
        }
    }

    public List<StatesModel> getAllStates(){
        Comparator<StatesModel> compareByConfirmed = (o1, o2) -> o2.getTotalConfirmed()-o1.getTotalConfirmed();
        Collections.sort(allStates,compareByConfirmed);
        return allStates;
    }

    public List<CountryModel> getAllCountries() {
        Comparator<CountryModel> compareByConfirmed = (o1, o2) -> o2.getTotalConfirmed()-o1.getTotalConfirmed();
        Collections.sort(allCountries,compareByConfirmed);
        return allCountries;
    }

    public WorldModel getWorld() {
        return worldModel;
    }

    public StatesModel getStateInfoByName(String name){
        for (StatesModel state: allStates){
            if (state.getState().equalsIgnoreCase(name))
                return state;
        }
        return null;
    }

    public CountryModel getCountryInfoByName(String name){
        for (CountryModel country: allCountries){
            if (country.getCountryName().equalsIgnoreCase(name))
                return country;
        }
        return null;
    }

    public List<StatesModel> getStateListByCountry(String name){
        for (CountryModel country: allCountries){
            if (country.getCountryName().equalsIgnoreCase(name)) {
                return country.getStates();
            }
        }
        return null;
    }


}
