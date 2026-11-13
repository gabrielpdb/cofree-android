package com.example.cofree.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.example.cofree.api.ApiResponse;
import com.example.cofree.ui.Input;
import com.example.cofree.ui.TextButton;
import com.example.cofree.users.UserRepository;
import com.example.cofree.validation.InputValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUp extends AppCompatActivity {

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();
    private UserRepository userRepository;
    private Input inputNome;
    private Input inputSobrenome;
    private Input inputNascimento;
    private Input inputEmail;
    private Input inputSenha;
    private Input inputConfirmarSenha;
    private TextButton buttonLogin;
    private TextButton buttonCadastrar;

    private Input inputTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepository = new UserRepository(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

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

        inputNome = findViewById(R.id.input_nome);
        inputSobrenome = findViewById(R.id.input_sobrenome);
        inputNascimento = findViewById(R.id.input_nascimento);
        inputEmail = findViewById(R.id.input_email);
        inputSenha = findViewById(R.id.input_senha);
        inputConfirmarSenha = findViewById(R.id.input_confirmar_senha);
        inputTelefone = findViewById(R.id.input_telefone);

        buttonCadastrar = findViewById(R.id.button_cadastrar);
        buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(v -> finish());

        buttonCadastrar.setOnClickListener(v -> realizarCadastro());
    }

    private void realizarCadastro() {

        boolean nomeValido = InputValidator.required(
                inputNome, "Nome"
        );

        boolean sobrenomeValido = InputValidator.required(
                inputSobrenome, "Sobrenome"
        );

        boolean telefoneValido = InputValidator.required(
                inputTelefone,
                "Telefone"
        );

        boolean nascimentoValido = InputValidator.date(
                inputNascimento, "Data de nascimento"
        );

        boolean emailValido = InputValidator.email(inputEmail);

        boolean senhaValida = InputValidator.required(
                inputSenha, "Senha"
        );

        boolean confirmacaoValida = InputValidator.required(
                inputConfirmarSenha, "Confirmação de senha"
        );

        boolean senhasIguais = false;

        if (senhaValida && confirmacaoValida) {

            senhasIguais = inputSenha.getValue().equals(
                    inputConfirmarSenha.getValue()
            );

            if (!senhasIguais) {
                inputConfirmarSenha.setError(
                        "As senhas não coincidem."
                );
            } else {
                inputConfirmarSenha.clearError();
            }
        }

        if (!nomeValido || !sobrenomeValido || !telefoneValido ||
                !nascimentoValido || !emailValido || !senhaValida ||
                !confirmacaoValida || !senhasIguais) {
            return;
        }

        String nome = inputNome.getValue().trim();
        String sobrenome = inputSobrenome.getValue().trim();
        String email = inputEmail.getValue().trim();
        String senha = inputSenha.getValue();
        String confirmacao = inputConfirmarSenha.getValue();
        String telefone = inputTelefone.getValue().trim();

        String nascimento;

        try {
            LocalDate date = LocalDate.parse(
                    inputNascimento.getValue().trim(),
                    DateTimeFormatter.ofPattern("dd/MM/uuuu")
            );

            nascimento = date.atStartOfDay(
                    ZoneId.of("America/Sao_Paulo")
            ).toInstant().toString();

        } catch (Exception e) {
            inputNascimento.setError("Data inválida.");
            return;
        }

        buttonCadastrar.setEnabled(false);

        executor.execute(() -> {

            try {

                ApiResponse response = userRepository.createUser(
                        nome,
                        sobrenome,
                        telefone,
                        nascimento,
                        email,
                        senha,
                        confirmacao
                );

                JSONObject json = new JSONObject(response.getBody());

                if (response.isSuccessful()) {

                    runOnUiThread(() -> {
                        Toast.makeText(
                                this,
                                "Cadastro realizado com sucesso!",
                                Toast.LENGTH_LONG
                        ).show();

                        finish();
                    });

                } else {

                    String mensagem = json.optString(
                            "message",
                            json.optString(
                                    "error",
                                    "Não foi possível realizar o cadastro."
                            )
                    );

                    runOnUiThread(() -> {
                        buttonCadastrar.setEnabled(true);

                        Toast.makeText(
                                this,
                                mensagem,
                                Toast.LENGTH_LONG
                        ).show();
                    });
                }

            } catch (IOException | JSONException e) {

                runOnUiThread(() -> {
                    buttonCadastrar.setEnabled(true);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
