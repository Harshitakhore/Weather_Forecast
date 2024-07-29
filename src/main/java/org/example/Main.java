package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.ApiClient;

public class Main extends AbstractVerticle {

    @Override
    public void start() {
        ApiClient apiClient = new ApiClient();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        WeatherHandler weatherHandler = new WeatherHandler(apiClient);

        router.get("/weather/forecast/summary/:city").handler(weatherHandler::getWeatherForecastSummary);
        router.get("/weather/forecast/hourly/:city").handler(weatherHandler::getHourlyForecast);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888, http -> {
                    if (http.succeeded()) {
                        System.out.println("HTTP server started on port 8888");
                    } else {
                        System.out.println("HTTP server failed to start");
                    }
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Main());
    }
}
