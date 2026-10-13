package com.example.cofree.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.example.cofree.model.SelectOption;
import com.example.cofree.ui.Input;
import com.example.cofree.ui.InputColor;
import com.example.cofree.ui.InputSelect;
import com.example.cofree.ui.TextButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreditCard extends AppCompatActivity {

    ImageView buttonBack;
    InputSelect inputBandeira;
    TextButton buttonExcluir;
    FloatingActionButton buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credit_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonBack = findViewById(R.id.button_back);
        buttonExcluir = findViewById(R.id.button_excluir);
        buttonSalvar = findViewById(R.id.button_salvar);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonSalvar.setOnClickListener(v -> {
            Toast.makeText(this, "Botão salvar clicado", Toast.LENGTH_SHORT).show();
        });
        buttonExcluir.setOnClickListener(v -> {
            Toast.makeText(this, "Botão excluir clicado", Toast.LENGTH_SHORT).show();
        });

        inputBandeira = findViewById(R.id.input_bandeira);

        inputBandeira.setOptions(new SelectOption[]{
                new SelectOption(1, "Visa"),
                new SelectOption(2, "Mastercard"),
        });
        inputBandeira.setSelectedId(1);
    }
}