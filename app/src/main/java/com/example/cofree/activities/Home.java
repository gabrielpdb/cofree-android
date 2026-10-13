package com.example.cofree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends AppCompatActivity {

    FloatingActionButton buttonContas;
    LinearLayout buttonReceitas;
    LinearLayout buttonDespesas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonContas = findViewById(R.id.button_contas);
        buttonReceitas = findViewById(R.id.button_receitas);
        buttonDespesas = findViewById(R.id.button_despesas);

        buttonContas.setOnClickListener(v -> {
            Intent intent = new Intent(this, Accounts.class);

            startActivity(intent);
        });
        buttonReceitas.setOnClickListener(v -> {
            Intent intent = new Intent(this, Transactions.class);

            startActivity(intent);
        });
        buttonDespesas.setOnClickListener(v -> {
            Intent intent = new Intent(this, Transactions.class);

            startActivity(intent);
        });
    }
}