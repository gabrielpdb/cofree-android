package com.example.cofree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.activities.PatrimonyEvolution;
import com.example.cofree.adapter.MovimentacaoAdapter;
import com.example.cofree.api.ApiClient;
import com.example.cofree.api.ApiResponse;
import com.example.cofree.auth.TokenManager;
import com.example.cofree.model.ItemListaMovimentacao;
import com.example.cofree.model.Movimentacao;
import com.example.cofree.model.PatrimonyDataPoint;
import com.example.cofree.ui.PatrimonyLineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home extends AppCompatActivity {

    private final String saldoOriginal = "R$ 12.345,67";
    private TextView txtSaldo;
    private ImageView buttonOcultarSaldo;
    private boolean saldoOculto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

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

        configurarPatrimonio();
        configurarGrafico();
        configurarUltimosLancamentos();
        configurarNavegacao();

        testApiConnection();
    }

    private void configurarPatrimonio() {
        txtSaldo = findViewById(R.id.txt_saldo);
        buttonOcultarSaldo = findViewById(R.id.button_ocultar_saldo);

        buttonOcultarSaldo.setOnClickListener(v -> {
            saldoOculto = !saldoOculto;

            if (saldoOculto) {
                txtSaldo.setText("R$ •••••••");
                buttonOcultarSaldo.setImageResource(
                        R.drawable.ic_visibility
                );
                buttonOcultarSaldo.setContentDescription(
                        "Exibir patrimônio"
                );
            } else {
                txtSaldo.setText(saldoOriginal);
                buttonOcultarSaldo.setImageResource(
                        R.drawable.ic_visibility_off
                );
                buttonOcultarSaldo.setContentDescription(
                        "Ocultar patrimônio"
                );
            }
        });
    }

    private void configurarGrafico() {
        PatrimonyLineChartView grafico =
                findViewById(R.id.grafico_evolucao_patrimonio);

        List<PatrimonyDataPoint> dados = new ArrayList<>();

        dados.add(new PatrimonyDataPoint("Mar", 10000));
        dados.add(new PatrimonyDataPoint("Abr", 15000));
        dados.add(new PatrimonyDataPoint("Mai", 21000));
        dados.add(new PatrimonyDataPoint("Jun", 18000));
        dados.add(new PatrimonyDataPoint("Jul", 28000));
        dados.add(new PatrimonyDataPoint("Ago", 33000));

        grafico.atualizarDados(dados);
    }

    private void configurarUltimosLancamentos() {
        RecyclerView recycler =
                findViewById(R.id.recycler_ultimos_lancamentos);

        recycler.setLayoutManager(
                new LinearLayoutManager(this)
        );

        List<ItemListaMovimentacao> itens = new ArrayList<>();

        itens.add(new ItemListaMovimentacao(
                new Movimentacao(
                        Movimentacao.Tipo.DESPESA,
                        "Mercado Santa Luzia",
                        128.00,
                        "15 de set.",
                        "Alimentação",
                        "Conta principal",
                        null,
                        "Compra no mercado",
                        null
                )
        ));

        itens.add(new ItemListaMovimentacao(
                new Movimentacao(
                        Movimentacao.Tipo.DESPESA,
                        "Padaria do Bairro",
                        32.50,
                        "15 de set.",
                        "Alimentação",
                        "Conta principal",
                        null,
                        "Compra na padaria",
                        null
                )
        ));

        itens.add(new ItemListaMovimentacao(
                new Movimentacao(
                        Movimentacao.Tipo.DESPESA,
                        "Posto Ipiranga",
                        311.00,
                        "13 de set.",
                        "Transporte",
                        "Conta principal",
                        null,
                        "Abastecimento",
                        null
                )
        ));

        itens.add(new ItemListaMovimentacao(
                new Movimentacao(
                        Movimentacao.Tipo.DESPESA,
                        "Netflix",
                        39.90,
                        "12 de set.",
                        "Assinaturas",
                        "Cartão de crédito",
                        null,
                        "Assinatura mensal",
                        null
                )
        ));

        itens.add(new ItemListaMovimentacao(
                new Movimentacao(
                        Movimentacao.Tipo.RECEITA,
                        "Freelance",
                        850.00,
                        "11 de set.",
                        "Trabalho",
                        "Conta principal",
                        null,
                        "Serviço realizado",
                        null
                )
        ));

        MovimentacaoAdapter adapter =
                new MovimentacaoAdapter(this, itens);

        recycler.setAdapter(adapter);
    }

    private void configurarNavegacao() {
        findViewById(R.id.button_evolucao_patrimonio)
                .setOnClickListener(v ->
                        startActivity(
                                new Intent(this, PatrimonyEvolution.class)
                        )
                );

        findViewById(R.id.button_contas)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, Accounts.class))
                );

        findViewById(R.id.button_receitas)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, Transactions.class))

                );

        findViewById(R.id.button_despesas)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, Transactions.class))

                );
    }

    private void abrirTela(String tela) {
        // A navegação será ligada às Activities correspondentes.
        // Por enquanto, evita abrir uma tela inexistente.
        android.widget.Toast.makeText(
                this,
                tela + ": navegação pendente",
                android.widget.Toast.LENGTH_SHORT
        ).show();
    }

    //TESTANDO CONEXÃO API

    private void testApiConnection() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            try {

                ApiClient apiClient = new ApiClient(Home.this);

                ApiResponse response = apiClient.request(
                        "/users/info",
                        "GET",
                        null
                );

                Log.d("API_TEST", "Status: " + response.getStatusCode());
                Log.d("API_TEST", "Body: " + response.getBody());

            } catch (Exception e) {

                Log.e("API_TEST", "Erro ao conectar com a API", e);

            } finally {

                executor.shutdown();

            }
        });
    }

}
