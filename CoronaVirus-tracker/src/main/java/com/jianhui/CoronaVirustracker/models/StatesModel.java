package com.jianhui.CoronaVirustracker.models;

import java.util.Map;

public class StatesModel {

    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private Map<String,Integer> dailyConfirmed;
    private Map<String,Integer> dailyRecovered;
    private Map<String,Integer> dailyDeath;

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

    public static class CoronaVirusCountryService {

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
}
