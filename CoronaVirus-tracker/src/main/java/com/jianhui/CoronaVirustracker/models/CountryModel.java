package com.jianhui.CoronaVirustracker.models;

import java.util.List;
import java.util.Map;

public class CountryModel implements Comparable< CountryModel>{

    private String countryName;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyRecovered;
    private Map<String,Integer> dailyDeath;
    private List<Map<String,String>> dailyList;
    private List<StatesModel> states;
    private int totalConfirmed;
    private int totalRecovered;
    private int totalDeath;
    private int confirmedRate;
    private int recoveredRate;
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

    public Map<String, Integer> getDailyRecovered() {
        return dailyRecovered;
    }

    public void setDailyRecovered(Map<String, Integer> dailyRecovered) {
        this.dailyRecovered = dailyRecovered;
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

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
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

    public int getRecoveredRate() {
        return recoveredRate;
    }

    public void setRecoveredRate(int recoveredRate) {
        this.recoveredRate = recoveredRate;
    }

    public int getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(int deathRate) {
        this.deathRate = deathRate;
    }

    @Override
    public int compareTo(CountryModel o) {
        return this.getTotalConfirmed() - o.getTotalConfirmed();
    }
}
