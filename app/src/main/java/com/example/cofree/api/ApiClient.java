package com.example.cofree.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    public HttpURLConnection createConnection(String endpoint, String method) throws IOException {
        // Monta o endereço
        URL url = new URL(ApiConfig.BASE_URL + endpoint);

        // Cria conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Seta conexão
        connection.setRequestMethod(method);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        // Retorna conexão
        return connection;
    }
}
