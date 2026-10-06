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
import com.example.cofree.ui.InputDate;
import com.example.cofree.ui.InputSelect;
import com.example.cofree.ui.InputTextArea;
import com.example.cofree.ui.TextButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Transfer extends AppCompatActivity {

    ImageView buttonBack;
    Input inputValor;
    InputDate inputData;
    InputTextArea inputObservacao;
    InputSelect inputContaOrigem;
    InputSelect inputContaDestino;

    TextButton buttonExcluir;
    FloatingActionButton buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transfer);
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

        inputValor = findViewById(R.id.input_valor);
        inputData = findViewById(R.id.input_data);
        inputContaOrigem = findViewById(R.id.input_conta_origem);
        inputContaDestino = findViewById(R.id.input_conta_destino);
        inputObservacao = findViewById(R.id.input_observacao);

        inputContaOrigem.setOptions(new SelectOption[]{
                new SelectOption(1, "Nubank", "R$ 1.250,00"),
                new SelectOption(2, "Inter", "R$ 850,00"),
                new SelectOption(3, "Carteira", "R$ 200,00")
        });
        inputContaOrigem.setSelectedId(1);
        inputContaDestino.setOptions(new SelectOption[]{
                new SelectOption(1, "Nubank", "R$ 1.250,00"),
                new SelectOption(2, "Inter", "R$ 850,00"),
                new SelectOption(3, "Carteira", "R$ 200,00")
        });
        inputContaDestino.setSelectedId(2);


    }
}