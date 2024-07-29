package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "https://forecast9.p.rapidapi.com/rapidapi/";
    private static final String RAPIDAPI_HOST = "forecast9.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "3b2c329b0fmshed77d4324ef6bcdp10ee15jsne7285ce0f516"; // Replace with your RapidAPI key
    private static final String APPLICATION_ID = "6277150"; // Replace with your Application ID if needed
    private ApiService apiService;
    private Gson gson;

    public ApiClient() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void getWeatherSummary(String city, Handler<AsyncResult<String>> resultHandler) {
        Call<WeatherResponse> call = apiService.getWeatherSummary(city, RAPIDAPI_HOST, RAPIDAPI_KEY, APPLICATION_ID);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    String jsonResponse = gson.toJson(weatherResponse);
                    System.out.println("Weather summary fetched: " + jsonResponse);
                    resultHandler.handle(Future.succeededFuture(jsonResponse));
                } else {
                    System.out.println("Request failed with code: " + response.code());
                    try {
                        System.out.println("Response body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    resultHandler.handle(Future.failedFuture("Failed to fetch weather summary. Code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                t.printStackTrace();
                resultHandler.handle(Future.failedFuture(t));
            }
        });
    }

    public void getHourlyForecast(String city, int hours, Handler<AsyncResult<String>> resultHandler) {
        Call<WeatherResponse> call = apiService.getHourlyForecast(city, hours, RAPIDAPI_HOST, RAPIDAPI_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Successful response
                    String json = gson.toJson(response.body());
                    resultHandler.handle(Future.succeededFuture(json));
                    System.out.println("Hourly forecast fetched successfully: " + json);
                } else {
                    // Unsuccessful response
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        errorMessage = "Unknown error";
                    }
                    resultHandler.handle(Future.failedFuture(new Exception("Failed to fetch hourly forecast. Code: " + response.code() + ", Message: " + errorMessage)));
                    System.out.println("Failed to fetch hourly forecast: Code: " + response.code() + ", Message: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Failure during the API call
                resultHandler.handle(Future.failedFuture(t));
                System.out.println("Failed to fetch hourly forecast: " + t.getMessage());
            }
        });
    }
}