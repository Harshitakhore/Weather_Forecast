package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends AbstractVerticle {

    @Override
    public void start() {
        String baseUrl = "https://forecast9.p.rapidapi.com/rapidapi/";
        ApiClient apiClient = new ApiClient(baseUrl);
        TokenHandler tokenHandler = new TokenHandler();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        WeatherHandler weatherHandler = new WeatherHandler(apiClient, tokenHandler);

        router.get("/weather/forecast/summary/:city").handler(weatherHandler::getWeatherForecastSummary);
        router.get("/weather/forecast/hourly/:city").handler(weatherHandler::getHourlyForecast);
        router.get("/generate-token").handler(tokenHandler::generateToken);

        // Log every request path
        router.route().handler(routingContext -> {
            System.out.println("Received request for path: " + routingContext.request().path());
            routingContext.next();
        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888, http -> {
                    if (http.succeeded()) {
                        System.out.println("HTTP server started on port 8888");

                        try {
                            InetAddress inetAddress = InetAddress.getLocalHost();
                            System.out.println("Server is running at: http://" + inetAddress.getHostAddress() + ":8888");
                        } catch (UnknownHostException e) {
                            System.err.println("Unable to determine local address");
                            e.printStackTrace();
                        }
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
