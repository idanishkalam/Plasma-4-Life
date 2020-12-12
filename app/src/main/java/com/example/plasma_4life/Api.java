package com.example.plasma_4life;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="https://disease.sh/v3/covid-19/";
    @GET("countries/India?yesterday=true&strict=true")
    Call<CovidData> getData();
}
