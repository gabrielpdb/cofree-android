package com.example.cofree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.adapter.CategoriaAdapter;
import com.example.cofree.model.Categoria;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView buttonBack = findViewById(R.id.button_back);
        ImageView buttonAddCategory = findViewById(R.id.button_add_category);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, Category.class);
            startActivity(intent);
        });


        RecyclerView recyclerCategorias = findViewById(R.id.recycler_categorias);

        MaterialButton buttonReceitas = findViewById(R.id.button_receitas);
        MaterialButton buttonDespesas = findViewById(R.id.button_despesas);

        List<Categoria> todasCategorias = new ArrayList<>();

        todasCategorias.add(new Categoria(
                1, "Salário", "#007BFF", Categoria.TipoCategoria.RECEITA
        ));
        todasCategorias.add(new Categoria(
                2, "Rendimentos", "#00A443", Categoria.TipoCategoria.RECEITA
        ));
        todasCategorias.add(new Categoria(
                3, "Extra", "#FF830F", Categoria.TipoCategoria.RECEITA
        ));
        todasCategorias.add(new Categoria(
                4, "Alimentação", "#F64141", Categoria.TipoCategoria.DESPESA
        ));
        todasCategorias.add(new Categoria(
                5, "Transporte", "#9C27B0", Categoria.TipoCategoria.DESPESA
        ));

        List<Categoria> categoriasExibidas = new ArrayList<>();

        CategoriaAdapter adapter = new CategoriaAdapter(categoriasExibidas);

        recyclerCategorias.setLayoutManager(new LinearLayoutManager(this));
        recyclerCategorias.setAdapter(adapter);

        buttonReceitas.setOnClickListener(v -> {
            filtrarCategorias(todasCategorias, categoriasExibidas,
                    adapter, Categoria.TipoCategoria.RECEITA);

            atualizarEstiloBotoes(
                    buttonReceitas, buttonDespesas, R.color.income);
        });

        buttonDespesas.setOnClickListener(v -> {
            filtrarCategorias(todasCategorias, categoriasExibidas,
                    adapter, Categoria.TipoCategoria.DESPESA);

            atualizarEstiloBotoes(
                    buttonDespesas, buttonReceitas, R.color.expense);
        });

        // Exibe receitas inicialmente
        buttonReceitas.performClick();

    }


    private void atualizarEstiloBotoes(
            MaterialButton selecionado,
            MaterialButton naoSelecionado,
            int corSelecionado) {

        selecionado.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        getColor(corSelecionado)
                )
        );

        selecionado.setTextColor(getColor(R.color.text_primary));

        naoSelecionado.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        getColor(R.color.surface)
                )
        );

        naoSelecionado.setTextColor(getColor(R.color.text_primary));
    }


    private void filtrarCategorias(
            List<Categoria> todas,
            List<Categoria> exibidas,
            CategoriaAdapter adapter,
            Categoria.TipoCategoria tipo) {

        exibidas.clear();

        for (Categoria categoria : todas) {
            if (categoria.getTipo() == tipo) {
                exibidas.add(categoria);
            }
        }

        adapter.notifyDataSetChanged();
    }

}