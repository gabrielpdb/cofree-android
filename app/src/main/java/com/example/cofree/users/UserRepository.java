package com.example.cofree.users;

import com.example.cofree.api.ApiClient;
import com.example.cofree.api.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserRepository {

    private final ApiClient apiClient;

    public UserRepository() {
        this.apiClient = new ApiClient();
    }

    public ApiResponse createUser(
            String name,
            String surname,
            String phone,
            String birthDate,
            String email,
            String password,
            String confirmPassword
    ) throws IOException, JSONException {

        JSONObject body = new JSONObject();

        body.put("name", name);
        body.put("surname", surname);
        body.put("phone", phone);
        body.put("birth_date", birthDate);
        body.put("email", email);
        body.put("password", password);
        body.put("confirm_password", confirmPassword);

        return apiClient.request(
                "/users",
                "POST",
                body
        );
    }
}
