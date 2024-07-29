package org.example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast/{city}/summary/")
    Call<WeatherResponse> getWeatherSummary(
            @Path("city") String city,
            @Header("x-rapidapi-host") String rapidApiHost,
            @Header("x-rapidapi-key") String rapidApiKey,
            @Header("x-application-id") String applicationId
    );

    @GET("forecast/{city}/hourly")
    Call<WeatherResponse> getHourlyForecast(
            @Path("city") String city,
            @Query("hours") int hours,
            @Header("x-rapidapi-host") String host,
            @Header("x-rapidapi-key") String apiKey
    );
}
