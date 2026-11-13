package com.example.cofree.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class ApiClient {

    public ApiResponse request(
            String endpoint,
            String method,
            JSONObject body
    ) throws IOException {

        HttpURLConnection connection = null;

        try {

            connection = createConnection(endpoint, method);

            connection.setRequestProperty(
                    "Accept",
                    "application/json"
            );

            if (body != null) {

                connection.setRequestProperty(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );

                connection.setDoOutput(true);

                try (OutputStream output = connection.getOutputStream()) {

                    output.write(
                            body.toString().getBytes(StandardCharsets.UTF_8)
                    );

                }

            }

            int statusCode = connection.getResponseCode();

            InputStream inputStream;

            if (statusCode >= 200 && statusCode < 400) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            String responseBody = readResponse(inputStream);

            return new ApiResponse(statusCode, responseBody);

        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }

    }

    private String readResponse(InputStream inputStream)
            throws IOException {

        if (inputStream == null) {
            return "";
        }

        StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )) {

            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        }

        return response.toString();

    }

    public HttpURLConnection createConnection(
            String endpoint,
            String method
    ) throws IOException {

        java.net.URL url = new java.net.URL(
                ApiConfig.BASE_URL + endpoint
        );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        return connection;

    }

}