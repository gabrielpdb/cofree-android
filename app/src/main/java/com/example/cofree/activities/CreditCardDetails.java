package com.example.cofree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.adapter.CreditCardExpenseAdapter;
import com.example.cofree.model.ItemListaMovimentacao;
import com.example.cofree.model.Movimentacao;
import com.example.cofree.ui.TextButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CreditCardDetails extends AppCompatActivity {

    ImageView buttonBack;
    ImageView buttonEditCard;
    ImageView buttonNewCreditExpense;
    TextButton buttonVerFatura;
    TextButton buttonPagarFatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credit_card_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonBack = findViewById(R.id.button_back);
        buttonEditCard = findViewById(R.id.button_edit_card);
        buttonNewCreditExpense = findViewById(R.id.button_new_credit_expense);

        buttonBack.setOnClickListener(v -> {
            finish();
        });
        buttonEditCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreditCard.class);
            startActivity(intent);
        });
        buttonNewCreditExpense.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreditCardExpense.class);
            startActivity(intent);
        });

        RecyclerView recyclerGastos = findViewById(
                R.id.recycler_gastos_credito
        );

        recyclerGastos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        List<Movimentacao> gastos = new ArrayList<>();

        gastos.add(new Movimentacao(
                Movimentacao.Tipo.CREDITO,
                "Supermercado",
                245.90,
                "2026-10-13",
                "Alimentação",
                "Sicredi",
                null,
                "3 de 3 parcelas",
                null
        ));

        gastos.add(new Movimentacao(
                Movimentacao.Tipo.CREDITO,
                "Loja de roupas",
                189.90,
                "2026-10-13",
                "Vestuário",
                "Sicredi",
                null,
                "À vista",
                null
        ));

        gastos.add(new Movimentacao(
                Movimentacao.Tipo.CREDITO,
                "Restaurante",
                76.00,
                "2026-10-12",
                "Alimentação",
                "Sicredi",
                null,
                "À vista",
                null
        ));

        gastos.add(new Movimentacao(
                Movimentacao.Tipo.CREDITO,
                "Eletrônicos",
                899.90,
                "2026-10-10",
                "Compras",
                "Sicredi",
                null,
                "2 de 10 parcelas",
                null
        ));

        gastos.sort(
                Comparator.comparing(Movimentacao::getData).reversed()
        );

        List<ItemListaMovimentacao> itens = new ArrayList<>();

        DateTimeFormatter formatoEntrada =
                DateTimeFormatter.ISO_LOCAL_DATE;

        DateTimeFormatter formatoSaida =
                DateTimeFormatter.ofPattern(
                        "EEEE, dd 'de' MMMM",
                        new Locale("pt", "BR")
                );

        String ultimaData = null;

        for (Movimentacao gasto : gastos) {
            if (!gasto.getData().equals(ultimaData)) {
                LocalDate data = LocalDate.parse(
                        gasto.getData(),
                        formatoEntrada
                );

                String dataFormatada = formatoSaida.format(data);

                dataFormatada = dataFormatada.substring(0, 1).toUpperCase(
                        new Locale("pt", "BR")
                ) + dataFormatada.substring(1);

                itens.add(new ItemListaMovimentacao(dataFormatada));

                ultimaData = gasto.getData();
            }

            itens.add(new ItemListaMovimentacao(gasto));
        }

        CreditCardExpenseAdapter adapter =
                new CreditCardExpenseAdapter(this, itens);

        recyclerGastos.setAdapter(adapter);

    }
}