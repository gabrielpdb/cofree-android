package com.example.cofree.api;

public class ApiResponse {

    private final int statusCode;
    private final String body;

    public ApiResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

}