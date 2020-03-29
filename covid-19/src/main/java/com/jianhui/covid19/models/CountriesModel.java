package com.jianhui.covid19.models;

import java.util.List;
import java.util.Map;

public class CountriesModel implements Comparable<CountriesModel>{

    private String countryName;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyDeath;
    private List<Map<String,String>> dailyList;
    private List<StatesModel> states;
    private int totalConfirmed;
    private int totalDeath;
    private int confirmedRate;
    private int deathRate;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Map<String, Integer> getDailyConfirmed() {
        return dailyConfirmed;
    }

    public void setDailyConfirmed(Map<String, Integer> dailyConfirmed) {
        this.dailyConfirmed = dailyConfirmed;
    }


    public Map<String, Integer> getDailyDeath() {
        return dailyDeath;
    }

    public void setDailyDeath(Map<String, Integer> dailyDeath) {
        this.dailyDeath = dailyDeath;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }

    public List<StatesModel> getStates() {
        return states;
    }

    public void setStates(List<StatesModel> states) {
        this.states = states;
    }

    public List<Map<String, String>> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<Map<String, String>> dailyList) {
        this.dailyList = dailyList;
    }

    public int getConfirmedRate() {
        return confirmedRate;
    }

    public void setConfirmedRate(int confirmedRate) {
        this.confirmedRate = confirmedRate;
    }

    public int getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(int deathRate) {
        this.deathRate = deathRate;
    }


    @Override
    public int compareTo(CountriesModel o) {
        return this.getTotalConfirmed() - o.getTotalConfirmed();
    }
}
