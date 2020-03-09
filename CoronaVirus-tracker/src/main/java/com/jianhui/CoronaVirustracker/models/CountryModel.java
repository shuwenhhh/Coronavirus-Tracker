package com.jianhui.CoronaVirustracker.models;

import java.util.Map;

public class CountryModel {

    private String countryName;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyRecovered;
    private Map<String,Integer> dailyDeath;

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

}
