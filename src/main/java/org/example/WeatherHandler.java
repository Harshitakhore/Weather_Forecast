package org.example;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class WeatherHandler {

    private final ApiClient apiClient;

    public WeatherHandler(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void getWeatherForecastSummary(RoutingContext context) {
        String city = context.pathParam("city");
        if (city == null || city.isEmpty()) {
            context.response()
                    .setStatusCode(400)
                    .end("City parameter is missing or empty");
            return;
        }

        apiClient.getWeatherSummary(city, result -> {
            if (result.succeeded()) {
                context.response()
                        .putHeader("content-type", "application/json")
                        .end(result.result());
            } else {
                context.response()
                        .setStatusCode(500)
                        .end("Failed to fetch weather summary: " + result.cause().getMessage());
            }
        });
    }

    public void getHourlyForecast(RoutingContext context) {
        String city = context.pathParam("city");
        String hoursParam = context.queryParam("hours").get(0);
        int hours;

        try {
            hours = Integer.parseInt(hoursParam);
        } catch (NumberFormatException e) {
            JsonObject errorResponse = new JsonObject()
                    .put("error", "Invalid hours parameter")
                    .put("code", 400);
            context.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(errorResponse.encodePrettily());
            return;
        }

        if (city == null || city.isEmpty()) {
            JsonObject errorResponse = new JsonObject()
                    .put("error", "City parameter is missing or empty")
                    .put("code", 400);
            context.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(errorResponse.encodePrettily());
            return;
        }

        apiClient.getHourlyForecast(city, hours, result -> {
            if (result.succeeded()) {
                context.response()
                        .putHeader("content-type", "application/json")
                        .end(result.result());
            } else {
                JsonObject errorResponse = new JsonObject()
                        .put("error", "Failed to fetch hourly forecast")
                        .put("code", 401) // Adjust this based on the actual error code
                        .put("message", result.cause().getMessage());
                context.response()
                        .setStatusCode(401) // Adjust this based on the actual error code
                        .putHeader("content-type", "application/json")
                        .end(errorResponse.encodePrettily());
            }
        });
    }
}