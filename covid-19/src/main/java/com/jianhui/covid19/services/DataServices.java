package com.jianhui.covid19.services;

import com.jianhui.covid19.models.CountriesModel;
import com.jianhui.covid19.models.StatesModel;
import com.jianhui.covid19.models.WorldModel;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataServices {
    private static final String CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private List<StatesModel> allStates = new ArrayList<>();
    private List<CountriesModel> allCountries = new ArrayList<>();
    private Set<String> countrySet = new HashSet<>();
    private WorldModel worldModel = new WorldModel();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void fetchData() throws IOException, InterruptedException, ParseException {
        List<StatesModel> newStates = new ArrayList<>();
        List<CountriesModel> newCountries = new ArrayList<>();
        HttpClient client = HttpClient.newBuilder().build();
        String[] urls = new String[]{CONFIRMED_URL, DEATH_URL};
        for (int i = 0; i < 2; ++i) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urls[i])).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            StringReader csvBodyReader = new StringReader(response.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
            if (i == 0)
                fetchConfirmedData(records, newStates);
            else
                fetchDeathData(records, newStates);
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
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            String lat = record.get("Lat");
            String lon = record.get("Long");
            if (lat.equalsIgnoreCase(lon))
                continue;
            if (state.length() == 0)
                tempState.setState("UNDEF");
            else
                tempState.setState(state);
            tempState.setCountry(country);
            this.countrySet.add(country);
            tempState.setLatitude(lat);
            tempState.setLongitude(lon);

            Map<String,Integer> map = new LinkedHashMap<>();
            int total = getStateDailyReport(map,record);
            int confirmedRate = 0;
            String latest = record.get(record.size()-1);
            if (latest.length() != 0)
                confirmedRate = Integer.parseInt(latest) - Integer.parseInt(record.get(record.size()-2));
            tempState.setConfirmedRate(confirmedRate);
            tempState.setDailyConfirmed(map);
            tempState.setTotalConfirmed(total);
            newStates.add(tempState);
            totalConfirmed += tempState.getTotalConfirmed();
        }
        worldModel.setTotalConfirmed(totalConfirmed);
    }

    private void fetchDeathData(Iterable<CSVRecord> records, List<StatesModel> newStates) throws ParseException{
        int index = 0;
        int totalDeath = 0;
        for (CSVRecord record : records){
            String lat = record.get("Lat");
            String lon = record.get("Long");
            if (lat.equalsIgnoreCase(lon))
                continue;
            Map<String,Integer> map = new LinkedHashMap<>();
            int total = getStateDailyReport(map,record);
            int deathRate = 0;
            String latest = record.get(record.size()-1);
            if (latest.length() != 0)
                deathRate = Integer.parseInt(latest) - Integer.parseInt(record.get(record.size()-2));
            newStates.get(index).setDeathRate(deathRate);
            newStates.get(index).setTotalDeath(total);
            newStates.get(index).setDailyDeath(map);
            totalDeath += newStates.get(index++).getTotalDeath();
        }
        worldModel.setTotalDeath(totalDeath);
    }

    private void fetchCountryData(List<StatesModel> newStates, List<CountriesModel> newCountries){
        for(String country: this.countrySet){
            List<StatesModel> list = new LinkedList<>();
            CountriesModel newCountry = new CountriesModel();
            newCountry.setCountryName(country);
            int countryTotalConfirmed=0;
            int countryTotalDeath=0;
            int deathRate = 0;
            int confirmedRate=0;
            List<Map<String,Integer>> confirmedDailyList = new LinkedList<>();
            List<Map<String,Integer>> deathDailyList = new LinkedList<>();
            for (StatesModel state: newStates){
                if (state.getCountry().equalsIgnoreCase(country)){
                    if (!state.getState().equalsIgnoreCase("UNDEF"))
                        list.add(state);
                    confirmedDailyList.add(state.getDailyConfirmed());
                    deathDailyList.add(state.getDailyDeath());
                    countryTotalConfirmed += state.getTotalConfirmed();
                    countryTotalDeath += state.getTotalDeath();
                    deathRate += state.getDeathRate();
                    confirmedRate += state.getConfirmedRate();
                }
            }
            newCountry.setTotalConfirmed(countryTotalConfirmed);
            newCountry.setTotalDeath(countryTotalDeath);
            newCountry.setDeathRate(deathRate);
            newCountry.setConfirmedRate(confirmedRate);
            newCountry.setStates(list);

            Map<String,Integer> confirmedDaily = new LinkedHashMap<>();
            Map<String,Integer> deathDaily = new LinkedHashMap<>();

            getCountryDailyReport(confirmedDaily,confirmedDailyList);
            getCountryDailyReport(deathDaily,deathDailyList);

            newCountry.setDailyConfirmed(confirmedDaily);
            newCountry.setDailyDeath(deathDaily);

            newCountries.add(newCountry);
        }
    }
    private void getWorldDailyReport() throws ParseException {
        String date = "1/22/20";
        String today = dateFormat.format(new Date());
        Map<String,Integer> confirmedDaily = new LinkedHashMap<>();
        Map<String, Integer> deathDaily = new LinkedHashMap<>();
        boolean isEnd = false;
        while (!date.equals(today)) {
            int confirmed=0,death=0;
            for (CountriesModel country : allCountries) {
                if (!country.getDailyConfirmed().containsKey(date)){
                    isEnd = true;
                    break;
                }
                confirmed += country.getDailyConfirmed().get(date);
                death += country.getDailyDeath().get(date);

            }
            if (isEnd)
                break;
            confirmedDaily.put(date, confirmed);
            deathDaily.put(date, death);
            date = plusOneDay(date);
        }
        int deathRate = 0;
        int confirmedRate=0;
        for (CountriesModel country: allCountries){
            deathRate += country.getDeathRate();
            confirmedRate += country.getConfirmedRate();
        }
        worldModel.setDeathRate(deathRate);
        worldModel.setConfirmedRate(confirmedRate);
        worldModel.setDailyConfirmed(confirmedDaily);
        worldModel.setDailyDeath(deathDaily);
    }

    public void getStateDailyList() throws ParseException{
        for (StatesModel state: allStates){
            Map<String,Integer> dailyConfirmed = state.getDailyConfirmed();
            Map<String,Integer> dailyDeath = state.getDailyDeath();
            List<Map<String,String>> list = new LinkedList<>();
            String date = "1/22/20";
            String today = dateFormat.format(new Date());
            while (!date.equals(today)){
                Map<String,String> map = new LinkedHashMap<>();
                map.put("date",date);
                map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
                map.put("death", String.valueOf(dailyDeath.get(date)));
                list.add(map);
                date = plusOneDay(date);
            }
            state.setDailyList(list);
        }
    }

    public void getCountryDailyList() throws ParseException{
        for (CountriesModel country: allCountries){
            Map<String,Integer> dailyConfirmed = country.getDailyConfirmed();
            Map<String,Integer> dailyDeath = country.getDailyDeath();
            List<Map<String,String>> list = new LinkedList<>();
            String date = "1/22/20";
            String today = dateFormat.format(new Date());
            while (!date.equals(today)){
                Map<String,String> map = new LinkedHashMap<>();
                map.put("date",date);
                map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
                map.put("death", String.valueOf(dailyDeath.get(date)));
                list.add(map);
                date = plusOneDay(date);
            }
            country.setDailyList(list);
        }
    }

    public void getWorldDailyList() throws ParseException {
        Map<String,Integer> dailyConfirmed = worldModel.getDailyConfirmed();
        Map<String,Integer> dailyDeath = worldModel.getDailyDeath();
        List<Map<String,String>> list = new LinkedList<>();
        String date = "1/22/20";
        String today = dateFormat.format(new Date());
        while (!date.equals(today)){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("date",date);
            map.put("confirmed", String.valueOf(dailyConfirmed.get(date)));
            map.put("death", String.valueOf(dailyDeath.get(date)));
            list.add(map);
            date = plusOneDay(date);
        }
        worldModel.setDailyList(list);
    }

    private int getStateDailyReport(Map<String,Integer> map, CSVRecord record) throws ParseException {
        String date = "1/22/20";
        int max = 0;
        int index = 4;
        while (index < record.size()){
            String num = record.get(index++);
            if (num.length() == 0)
                break;
            max = Math.max(Integer.parseInt(num),max);
            map.put(date,max);
            date = plusOneDay(date);
        }
        return max;
    }
    private void getCountryDailyReport (Map<String,Integer> map, List<Map<String,Integer>> mapList){
        for (Map<String,Integer> daily: mapList) {
            for (String date : daily.keySet()) {
                map.put(date, map.getOrDefault(date, 0) + daily.get(date));
            }
        }
    }

    private String plusOneDay(String dt) throws ParseException {
        Date date = dateFormat.parse(dt);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return dateFormat.format(c.getTime());
    }

    public List<CountriesModel> getAllCountries() {
        Comparator<CountriesModel> compareByConfirmed = (o1, o2) -> o2.getTotalConfirmed()-o1.getTotalConfirmed();
        Collections.sort(allCountries,compareByConfirmed);
        return allCountries;
    }

    public List<StatesModel> getAllStates(){
        return allStates;
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

    public CountriesModel getCountryInfoByName(String name){
        for (CountriesModel country: allCountries){
            if (country.getCountryName().equalsIgnoreCase(name))
                return country;
        }
        return null;
    }

    public List<StatesModel> getStateListByCountry(String name){
        for (CountriesModel country: allCountries){
            if (country.getCountryName().equalsIgnoreCase(name)) {
                return country.getStates();
            }
        }
        return null;
    }


}
