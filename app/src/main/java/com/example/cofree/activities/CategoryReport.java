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
import com.example.cofree.adapter.CategoriaRelatorioAdapter;
import com.example.cofree.model.ItemCategoriaRelatorio;
import com.example.cofree.ui.DonutChartView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerCategorias =
                findViewById(R.id.recycler_categorias_relatorio);

        DonutChartView grafico =
                findViewById(R.id.grafico_donut);

        MaterialButton buttonReceitas =
                findViewById(R.id.button_receitas);

        MaterialButton buttonDespesas =
                findViewById(R.id.button_despesas);

        recyclerCategorias.setLayoutManager(
                new LinearLayoutManager(this));

// Dados fictícios de receitas
        List<ItemCategoriaRelatorio> receitas = new ArrayList<>();
        receitas.add(new ItemCategoriaRelatorio(
                "Salário", "#00C5D4", 3500.00));
        receitas.add(new ItemCategoriaRelatorio(
                "Rendimento", "#804526", 125.50));
        receitas.add(new ItemCategoriaRelatorio(
                "Outros", "#CC0000", 200.00));

// Dados fictícios de despesas
        List<ItemCategoriaRelatorio> despesas = new ArrayList<>();
        despesas.add(new ItemCategoriaRelatorio(
                "Aluguel", "#00C5D4", 600.00));
        despesas.add(new ItemCategoriaRelatorio(
                "Gasolina", "#804526", 1200.00));
        despesas.add(new ItemCategoriaRelatorio(
                "Tigrinho", "#CC0000", 3511.50));

        CategoriaRelatorioAdapter adapter =
                new CategoriaRelatorioAdapter(this, despesas);

        recyclerCategorias.setAdapter(adapter);

        buttonReceitas.setOnClickListener(v -> {
            adapter.atualizarCategorias(receitas);

            grafico.atualizarDados(
                    new String[]{"Salário", "Rendimento", "Outros"},
                    new double[]{3500.00, 125.50, 200.00},
                    new int[]{0xFF00C5D4, 0xFF804526, 0xFFCC0000}
            );

            atualizarSelecao(buttonReceitas, buttonDespesas);
        });

        buttonDespesas.setOnClickListener(v -> {
            adapter.atualizarCategorias(despesas);

            grafico.atualizarDados(
                    new String[]{"Aluguel", "Gasolina", "Tigrinho"},
                    new double[]{600.00, 1200.00, 3511.50},
                    new int[]{0xFF00C5D4, 0xFF804526, 0xFFCC0000}
            );

            atualizarSelecao(buttonDespesas, buttonReceitas);
        });

        // Estado inicial
        buttonDespesas.performClick();
    }

    private void atualizarSelecao(
            MaterialButton selecionado,
            MaterialButton naoSelecionado) {

        selecionado.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        selecionado.getId() == R.id.button_receitas
                                ? getColor(R.color.income)
                                : getColor(R.color.expense)));

        selecionado.setTextColor(getColor(R.color.white));

        naoSelecionado.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        getColor(R.color.surface)));

        naoSelecionado.setTextColor(getColor(R.color.text_primary));
    }
}