package com.example.cofree.activities;

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
import com.example.cofree.ui.InputDate;
import com.example.cofree.ui.InputSelect;
import com.example.cofree.ui.InputTextArea;
import com.example.cofree.ui.TextButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Income extends AppCompatActivity {

    ImageView buttonBack;
    Input inputValor;
    Input inputTitulo;
    InputDate inputData;
    InputTextArea inputObservacao;
    InputSelect inputCategoria;
    InputSelect inputConta;

    TextButton buttonExcluir;
    FloatingActionButton buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        inputValor = findViewById(R.id.input_valor);
        inputTitulo = findViewById(R.id.input_titulo);
        inputData = findViewById(R.id.input_data);
        inputCategoria = findViewById(R.id.input_categoria);
        inputConta = findViewById(R.id.input_conta);
        inputObservacao = findViewById(R.id.input_observacao);
        buttonSalvar = findViewById(R.id.button_salvar);
        buttonExcluir = findViewById(R.id.button_excluir);

        //Aqui entram os dados da api
        inputCategoria.setOptions(new SelectOption[]{
                new SelectOption(1, "Alimentação"),
                new SelectOption(2, "Salário"),
                new SelectOption(3, "Lazer")
        });
        inputCategoria.setSelectedId(2);
        inputConta.setOptions(new SelectOption[]{
                new SelectOption(1, "Nubank", "R$ 1.250,00"),
                new SelectOption(2, "Inter", "R$ 850,00"),
                new SelectOption(3, "Carteira", "R$ 200,00")
        });
        inputConta.setSelectedId(3);


        buttonSalvar.setOnClickListener(v -> {
            Toast.makeText(this, "Botão salvar clicado", Toast.LENGTH_SHORT).show();
        });
        buttonExcluir.setOnClickListener(v -> {
            Toast.makeText(this, "Botão excluir clicado", Toast.LENGTH_SHORT).show();
        });
    }
}