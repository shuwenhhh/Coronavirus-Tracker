package com.jianhui.covid19.models;

import java.util.List;
import java.util.Map;

public class WorldModel {

    private int totalConfirmed;
    private int totalDeath;
    private int confirmedRate;
    private int deathRate;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyDeath;
    private List<Map<String,String>> dailyList;


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

    public List<Map<String, String>> getDailyList() {
        return dailyList;
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


    public void setDailyList(List<Map<String, String>> dailyList) {
        this.dailyList = dailyList;
    }
}
