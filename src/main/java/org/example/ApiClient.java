package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "https://forecast9.p.rapidapi.com/rapidapi/";
    private static final String RAPIDAPI_HOST = "forecast9.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "3b2c329b0fmshed77d4324ef6bcdp10ee15jsne7285ce0f516";
    private static final String APPLICATION_ID = "6277150";
    private ApiService apiService;
    private Gson gson;
    private String token;

    public ApiClient(String token) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.token = token;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    return chain.proceed(chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .addHeader("x-rapidapi-host", RAPIDAPI_HOST)
                            .addHeader("x-rapidapi-key", RAPIDAPI_KEY)
                            .addHeader("x-application-id", APPLICATION_ID)
                            .build());
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void getWeatherSummary(String city, Handler<AsyncResult<String>> resultHandler) {
        Call<WeatherResponse> call = apiService.getWeatherSummary(city);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    String jsonResponse = gson.toJson(weatherResponse);
                    resultHandler.handle(Future.succeededFuture(jsonResponse));
                } else {
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        errorMessage = "Unknown error";
                    }
                    resultHandler.handle(Future.failedFuture("Failed to fetch weather summary. Code: " + response.code() + ", Message: " + errorMessage));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                resultHandler.handle(Future.failedFuture(t));
            }
        });
    }

    public void getHourlyForecast(String city, int hours, Handler<AsyncResult<String>> resultHandler) {
        Call<WeatherResponse> call = apiService.getHourlyForecast(city, hours);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    String json = gson.toJson(response.body());
                    resultHandler.handle(Future.succeededFuture(json));
                } else {
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        errorMessage = "Unknown error";
                    }
                    resultHandler.handle(Future.failedFuture(new Exception("Failed to fetch hourly forecast. Code: " + response.code() + ", Message: " + errorMessage)));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                resultHandler.handle(Future.failedFuture(t));
            }
        });
    }
}
