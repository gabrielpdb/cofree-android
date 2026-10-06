package com.example.cofree;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.ui.TextButton;

public class MainActivity extends AppCompatActivity {

    TextButton buttonCadastro;
    TextButton buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        buttonCadastro = findViewById(R.id.button_cadastro);
        buttonEntrar = findViewById(R.id.button_entrar);

        buttonCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUp.class);

            startActivity(intent);
        });

        buttonEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, Account.class);

            startActivity(intent);
        });
    }
}