package com.example.cofree.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.adapter.MovimentacaoAdapter;
import com.example.cofree.model.ItemListaMovimentacao;
import com.example.cofree.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transactions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerMovimentacoes =
                findViewById(R.id.recycler_movimentacoes);

        List<Movimentacao> movimentacoes = new ArrayList<>();

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.RECEITA,
                "Salário",
                3500.00,
                "2026-09-15",
                "Trabalho",
                "Conta corrente",
                null,
                null,
                null
        ));

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.DESPESA,
                "Supermercado",
                -250.00,
                "2026-09-15",
                "Alimentação",
                "Carteira",
                null,
                null,
                null
        ));

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.TRANSFERENCIA,
                "Transferência",
                500.00,
                "2026-09-14",
                null,
                "Carteira",
                "Conta corrente",
                null,
                null
        ));

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.CREDITO,
                "Compra no cartão",
                -180.00,
                "2026-09-13",
                "Eletrônicos",
                "Cartão Nubank",
                null,
                "Fatura de outubro",
                null
        ));

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.RENDIMENTO,
                "Rendimento",
                25.50,
                "2026-09-12",
                null,
                "Poupança",
                null,
                "30 dias",
                "0,80%"
        ));

        movimentacoes.add(new Movimentacao(
                Movimentacao.Tipo.ALTERACAO,
                "Alteração de saldo",
                100.00,
                "2026-09-11",
                null,
                "Carteira",
                null,
                "Correção manual",
                null
        ));


        movimentacoes.sort((a, b) -> b.getData().compareTo(a.getData()));

        List<ItemListaMovimentacao> itens = new ArrayList<>();
        String ultimaData = "";

        java.text.SimpleDateFormat formatoData =
                new java.text.SimpleDateFormat(
                        "EEEE, d 'de' MMMM",
                        new java.util.Locale("pt", "BR"));

        for (Movimentacao movimentacao : movimentacoes) {
            if (!movimentacao.getData().equals(ultimaData)) {
                try {
                    java.util.Date data = new java.text.SimpleDateFormat(
                            "yyyy-MM-dd",
                            java.util.Locale.US
                    ).parse(movimentacao.getData());

                    String dataFormatada = formatoData.format(data);

                    // Primeira letra maiúscula
                    dataFormatada = dataFormatada.substring(0, 1).toUpperCase(
                            new java.util.Locale("pt", "BR"))
                            + dataFormatada.substring(1);

                    itens.add(new ItemListaMovimentacao(dataFormatada));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                ultimaData = movimentacao.getData();
            }

            itens.add(new ItemListaMovimentacao(movimentacao));
        }

        MovimentacaoAdapter adapter = new MovimentacaoAdapter(this, itens);

        recyclerMovimentacoes.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerMovimentacoes.setAdapter(adapter);


    }
}