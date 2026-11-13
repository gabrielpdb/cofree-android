package com.example.cofree.api;

import android.content.Context;

import com.example.cofree.auth.SessionManager;
import com.example.cofree.auth.TokenManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class ApiClient {

    private final TokenManager tokenManager;

    private final Context context;

    public ApiClient(Context context) {
        this.context = context;
        this.tokenManager = new TokenManager(context);
    }


    public ApiResponse request(
            String endpoint,
            String method,
            JSONObject body
    ) throws IOException {

        ApiResponse response = executarRequisicao(endpoint, method, body);

        boolean rotaPublica = endpoint.equals("/login")
                || endpoint.equals("/login/refresh")
                || endpoint.equals("/users");

        if (response.getStatusCode() == 401 && !rotaPublica) {

            if (renovarToken()) {
                return executarRequisicao(endpoint, method, body);
            }
        }

        return response;
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


    private ApiResponse executarRequisicao(
            String endpoint,
            String method,
            JSONObject body
    ) throws IOException {

        HttpURLConnection connection = null;

        try {
            connection = createConnection(endpoint, method);

            connection.setRequestProperty("Accept", "application/json");

            String token = tokenManager.getToken();

            if (token != null && !token.isEmpty()
                    && !endpoint.equals("/login")
                    && !endpoint.equals("/login/refresh")
                    && !endpoint.equals("/users")) {

                connection.setRequestProperty(
                        "Authorization",
                        "Bearer " + token
                );
            }

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

            InputStream inputStream = statusCode >= 200 && statusCode < 400
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            String responseBody = readResponse(inputStream);

            return new ApiResponse(statusCode, responseBody);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    private synchronized boolean renovarToken() throws IOException {

        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            new SessionManager(context).logout();
            return false;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("refresh_token", refreshToken);

            ApiResponse response = executarRequisicao(
                    "/login/refresh",
                    "POST",
                    body
            );

            if (!response.isSuccessful()) {

                if (response.getStatusCode() == 401
                        || response.getStatusCode() == 403) {

                    new SessionManager(context).logout();
                }

                return false;
            }

            JSONObject json = new JSONObject(response.getBody());

            String novoToken = json.getString("token");

            String novoRefreshToken = json.optString(
                    "refresh_token",
                    refreshToken
            );

            tokenManager.saveTokens(novoToken, novoRefreshToken);

            return true;

        } catch (org.json.JSONException e) {
            throw new IOException("Erro ao processar renovação do token.", e);
        }
    }

}