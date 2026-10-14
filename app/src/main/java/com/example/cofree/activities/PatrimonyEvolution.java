package com.example.cofree.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.example.cofree.model.PatrimonyDataPoint;
import com.example.cofree.ui.PatrimonyLineChartView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PatrimonyEvolution extends AppCompatActivity {

    private final NumberFormat formatoMoeda =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final NumberFormat formatoPercentual =
            NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    private TextView textPeriodo;
    private TextView textPatrimonioTotal;
    private TextView textPercentualVariacao;
    private TextView textPeriodoVariacao;
    private TextView textValorInicial;
    private TextView textVariacaoValor;
    private TextView textVariacaoPercentualCard;
    private TextView textValorFinal;
    private TextView textSetaVariacao;
    private PatrimonyLineChartView grafico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patrimony_evolution);

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

        textPeriodo = findViewById(R.id.text_periodo);
        textPatrimonioTotal = findViewById(R.id.text_patrimonio_total);
        textPercentualVariacao =
                findViewById(R.id.text_percentual_variacao);
        textPeriodoVariacao = findViewById(R.id.text_periodo_variacao);
        textValorInicial = findViewById(R.id.text_valor_inicial);
        textVariacaoValor = findViewById(R.id.text_variacao_valor);
        textVariacaoPercentualCard =
                findViewById(R.id.text_variacao_percentual_card);
        textValorFinal = findViewById(R.id.text_valor_final);
        textSetaVariacao = findViewById(R.id.text_seta_variacao);

        grafico = findViewById(R.id.grafico_evolucao_patrimonio);

        formatoPercentual.setMinimumFractionDigits(1);
        formatoPercentual.setMaximumFractionDigits(1);

        LinearLayout seletorPeriodo =
                findViewById(R.id.seletor_periodo);

        seletorPeriodo.setOnClickListener(v -> abrirSeletorPeriodo());

        // Período inicial.
        atualizarPeriodo("6 meses");
    }

    private void abrirSeletorPeriodo() {
        PopupMenu menu = new PopupMenu(this, textPeriodo);

        menu.getMenu().add("1 mês");
        menu.getMenu().add("6 meses");
        menu.getMenu().add("1 ano");
        menu.getMenu().add("5 anos");

        menu.setOnMenuItemClickListener(item -> {
            atualizarPeriodo(item.getTitle().toString());
            return true;
        });

        menu.show();
    }

    private void atualizarPeriodo(String periodo) {
        List<PatrimonyDataPoint> dados = gerarDados(periodo);

        grafico.atualizarDados(dados);
        textPeriodo.setText(periodo);

        double valorInicial = dados.get(0).getValor();
        double valorFinal = dados.get(dados.size() - 1).getValor();

        double variacao = valorFinal - valorInicial;

        double percentual = valorInicial != 0
                ? (variacao / valorInicial) * 100
                : 0;

        textPatrimonioTotal.setText(formatarMoeda(valorFinal));
        textValorInicial.setText(formatarMoeda(valorInicial));
        textValorFinal.setText(formatarMoeda(valorFinal));

        String sinal = variacao > 0 ? "+" : "";

        textVariacaoValor.setText(
                sinal + formatarMoeda(variacao)
        );

        String percentualFormatado =
                formatoPercentual.format(percentual) + "%";

        textPercentualVariacao.setText(percentualFormatado);

        textVariacaoPercentualCard.setText(
                "(" + sinal + percentualFormatado + ")"
        );

        String descricaoPeriodo;

        switch (periodo) {
            case "1 mês":
                descricaoPeriodo = "no último mês";
                break;

            case "1 ano":
                descricaoPeriodo = "no último ano";
                break;

            case "5 anos":
                descricaoPeriodo = "nos últimos 5 anos";
                break;

            default:
                descricaoPeriodo = "nos últimos 6 meses";
                break;
        }

        textPeriodoVariacao.setText(descricaoPeriodo);

        boolean positivo = variacao >= 0;

        int cor = ContextCompat.getColor(
                this,
                positivo ? R.color.income : R.color.expense
        );

        textPercentualVariacao.setTextColor(cor);
        textVariacaoValor.setTextColor(cor);
        textSetaVariacao.setTextColor(cor);

        textSetaVariacao.setText(positivo ? "↑" : "↓");
    }

    private List<PatrimonyDataPoint> gerarDados(String periodo) {
        List<PatrimonyDataPoint> dados = new ArrayList<>();

        switch (periodo) {
            case "1 mês":
                dados.add(new PatrimonyDataPoint("13/09", 11000));
                dados.add(new PatrimonyDataPoint("19/09", 11500));
                dados.add(new PatrimonyDataPoint("25/09", 11200));
                dados.add(new PatrimonyDataPoint("01/10", 12100));
                dados.add(new PatrimonyDataPoint("07/10", 11900));
                dados.add(new PatrimonyDataPoint("13/10", 12345.67));
                break;

            case "1 ano":
                dados.add(new PatrimonyDataPoint("Nov", 8000));
                dados.add(new PatrimonyDataPoint("Jan", 9200));
                dados.add(new PatrimonyDataPoint("Mar", 10000));
                dados.add(new PatrimonyDataPoint("Mai", 11000));
                dados.add(new PatrimonyDataPoint("Jul", 11800));
                dados.add(new PatrimonyDataPoint("Out", 12345.67));
                break;

            case "5 anos":
                dados.add(new PatrimonyDataPoint("2022", 4000));
                dados.add(new PatrimonyDataPoint("2023", 6000));
                dados.add(new PatrimonyDataPoint("2024", 7500));
                dados.add(new PatrimonyDataPoint("2025", 10000));
                dados.add(new PatrimonyDataPoint("2026", 12345.67));
                break;

            default: // 6 meses
                dados.add(new PatrimonyDataPoint("Mai", 10000));
                dados.add(new PatrimonyDataPoint("Jun", 15000));
                dados.add(new PatrimonyDataPoint("Jul", 21000));
                dados.add(new PatrimonyDataPoint("Ago", 18000));
                dados.add(new PatrimonyDataPoint("Set", 28000));
                dados.add(new PatrimonyDataPoint("Out", 33000));
                break;
        }

        return dados;
    }

    private String formatarMoeda(double valor) {
        return formatoMoeda.format(valor);
    }
}
