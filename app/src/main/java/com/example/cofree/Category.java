package com.example.cofree;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.ui.Input;
import com.example.cofree.ui.InputColor;
import com.example.cofree.ui.InputDate;
import com.example.cofree.ui.InputSelect;
import com.example.cofree.ui.InputTextArea;
import com.example.cofree.ui.TextButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Category extends AppCompatActivity {

    ImageView buttonBack;
    InputSelect inputTipo;
    Input inputNome;
    InputColor inputCor;
    TextButton buttonExcluir;
    FloatingActionButton buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
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

        inputTipo = findViewById(R.id.input_tipo);
        inputNome = findViewById(R.id.input_nome);
        inputCor = findViewById(R.id.input_cor);

        inputTipo.setOptions(new SelectOption[]{
                new SelectOption(1, "Receita"),
                new SelectOption(2, "Despesa"),
        });
        inputTipo.setSelectedId(1);


    }
}