package org.example;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class TokenHandler {
    private String storedToken;

    // Method to generate and store the token
    public void generateToken(RoutingContext context) {
        // Generate a simple token (UUID for demonstration purposes)
        storedToken = UUID.randomUUID().toString();

        // Print the token to the console
        System.out.println("Generated Token: " + storedToken);

        // Return the token in the response
        JsonObject response = new JsonObject().put("token", storedToken);
        context.response()
                .putHeader("content-type", "application/json")
                .end(response.encode());
    }

    // Method to verify if the token is valid
    public boolean verifyToken(String token) {
        return token != null && token.equals(storedToken);
    }
}
