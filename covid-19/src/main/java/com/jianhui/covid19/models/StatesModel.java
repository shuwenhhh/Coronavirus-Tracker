package com.jianhui.covid19.models;

import java.util.List;
import java.util.Map;

public class StatesModel implements Comparable<StatesModel>{

    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private int totalConfirmed;
    private int totalDeath;
    private int confirmedRate;
    private int deathRate;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyDeath;
    private List<Map<String,String>> dailyList;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
    public int compareTo(StatesModel o) {
        return this.getTotalConfirmed() - o.getTotalConfirmed();
    }
}