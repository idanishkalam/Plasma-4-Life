package com.example.plasma_4life;

public class CovidData {
    long  cases,recovered,deaths,active;

    public CovidData(long cases,long deaths, long recovered,  long active) {
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
    }

    public long getCases() {
        return cases;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getActive() {
        return active;
    }
}
