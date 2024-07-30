package org.example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast/{city}/summary/")
    Call<WeatherResponse> getWeatherSummary(
            @Path("city") String city
    );

    @GET("forecast/{city}/hourly")
    Call<WeatherResponse> getHourlyForecast(
            @Path("city") String city,
            @Query("hours") int hours
    );
}
