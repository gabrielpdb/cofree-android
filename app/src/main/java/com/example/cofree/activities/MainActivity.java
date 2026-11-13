package com.example.cofree.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.example.cofree.api.ApiResponse;
import com.example.cofree.auth.AuthRepository;
import com.example.cofree.ui.Input;
import com.example.cofree.ui.TextButton;
import com.example.cofree.validation.InputValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final AuthRepository authRepository = new AuthRepository();
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();
    private Input inputEmail;
    private Input inputSenha;
    private TextButton buttonCadastro;
    private TextButton buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        inputEmail = findViewById(R.id.input_email);
        inputSenha = findViewById(R.id.input_senha);

        buttonCadastro = findViewById(R.id.button_cadastro);
        buttonEntrar = findViewById(R.id.button_entrar);

        buttonCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        buttonEntrar.setOnClickListener(v -> realizarLogin());
    }

    private void realizarLogin() {

        boolean emailValido = InputValidator.email(inputEmail);

        boolean senhaValida = InputValidator.required(
                inputSenha,
                "Senha"
        );

        if (!emailValido || !senhaValida) {
            return;
        }

        String email = inputEmail.getValue().trim();
        String senha = inputSenha.getValue();

        buttonEntrar.setEnabled(false);

        executor.execute(() -> {

            try {

                ApiResponse response = authRepository.login(email, senha);

                JSONObject json = new JSONObject(response.getBody());

                if (response.isSuccessful()) {

                    String token = json.getString("token");
                    String refreshToken = json.getString("refresh_token");

                    JSONObject user = json.getJSONObject("user");

                    salvarAutenticacao(token, refreshToken, user);

                    runOnUiThread(() -> {
                        Toast.makeText(
                                this,
                                "Login realizado com sucesso!",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent = new Intent(this, Home.class);
                        startActivity(intent);
                        finish();
                    });

                } else {

                    String mensagem = json.optString(
                            "error",
                            "Não foi possível realizar o login."
                    );

                    runOnUiThread(() -> {
                        buttonEntrar.setEnabled(true);

                        Toast.makeText(
                                this,
                                mensagem,
                                Toast.LENGTH_LONG
                        ).show();
                    });
                }

            } catch (IOException | JSONException e) {

                runOnUiThread(() -> {
                    buttonEntrar.setEnabled(true);

                    Toast.makeText(
                            this,
                            "Erro ao conectar com o servidor.",
                            Toast.LENGTH_LONG
                    ).show();
                });

                e.printStackTrace();
            }
        });
    }

    private void salvarAutenticacao(
            String token,
            String refreshToken,
            JSONObject user
    ) {

        SharedPreferences preferences = getSharedPreferences(
                "cofree_auth",
                MODE_PRIVATE
        );

        preferences.edit()
                .putString("token", token)
                .putString("refresh_token", refreshToken)
                .putString("user", user.toString())
                .apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
