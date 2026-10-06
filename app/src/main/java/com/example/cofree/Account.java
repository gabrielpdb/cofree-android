package com.example.cofree;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.ui.Input;
import com.example.cofree.ui.InputColor;
import com.example.cofree.ui.InputSelect;
import com.example.cofree.ui.TextButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Account extends AppCompatActivity {
    ImageView buttonBack;
    TextButton buttonExcluir;
    FloatingActionButton buttonSalvar;

    Input inputNome;
    Input inputValor;
    InputSelect inputTipo;
    InputColor inputCor;
    CheckBox inputRendimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
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


        inputNome = findViewById(R.id.input_nome);
        inputValor = findViewById(R.id.input_valor);
        inputTipo = findViewById(R.id.input_tipo);
        inputTipo.setOptions(new SelectOption[]{
                new SelectOption(1, "Dinheiro"),
                new SelectOption(2, "Conta Corrente"),
                new SelectOption(3, "Investimento"),
                new SelectOption(4, "Conta Poupança"),
                new SelectOption(5, "Outro"),
        });
        inputTipo.setSelectedId(1);

        inputCor = findViewById(R.id.input_cor);
        inputRendimento = findViewById(R.id.input_rendimento);

    }
}