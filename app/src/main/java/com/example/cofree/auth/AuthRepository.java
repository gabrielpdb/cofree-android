package com.example.cofree.auth;

import com.example.cofree.api.ApiClient;
import com.example.cofree.api.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthRepository {

    private final ApiClient apiClient;

    public AuthRepository() {
        this.apiClient = new ApiClient();
    }

    public ApiResponse login(String email, String password)
            throws IOException, JSONException {

        // JSON enviado no request
        JSONObject body = new JSONObject();

        body.put("email", email);
        body.put("password", password);

        return apiClient.request(
                "/login",
                "POST",
                body
        );

    }

}