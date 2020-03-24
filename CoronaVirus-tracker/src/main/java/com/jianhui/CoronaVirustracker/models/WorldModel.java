package com.jianhui.CoronaVirustracker.models;

import java.util.List;
import java.util.Map;

public class WorldModel {

    private int totalConfirmed;
    private int totalRecovered;
    private int totalDeath;
    private int confirmedRate;
    private int recoveredRate;
    private int deathRate;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyRecovered;
    private Map<String,Integer> dailyDeath;
    private List<Map<String,String>> dailyList;


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

    public List<Map<String, String>> getDailyList() {
        return dailyList;
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

    public void setDailyList(List<Map<String, String>> dailyList) {
        this.dailyList = dailyList;
    }
}
