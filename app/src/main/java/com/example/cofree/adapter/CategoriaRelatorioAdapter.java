package com.example.cofree.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.model.ItemCategoriaRelatorio;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CategoriaRelatorioAdapter
        extends RecyclerView.Adapter<CategoriaRelatorioAdapter.ViewHolder> {

    private final Context context;
    private List<ItemCategoriaRelatorio> categorias;

    public CategoriaRelatorioAdapter(
            Context context,
            List<ItemCategoriaRelatorio> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_categoria_relatorio, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        ItemCategoriaRelatorio categoria = categorias.get(position);

        holder.textNome.setText(categoria.getNome());

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        holder.textValor.setText(formatoMoeda.format(categoria.getValor()));

        try {
            int cor = Color.parseColor(categoria.getCor());

            holder.viewCor.setBackgroundTintList(
                    ColorStateList.valueOf(cor));

        } catch (IllegalArgumentException e) {
            holder.viewCor.setBackgroundTintList(
                    ColorStateList.valueOf(Color.GRAY));
        }
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void atualizarCategorias(
            List<ItemCategoriaRelatorio> novasCategorias) {

        categorias = novasCategorias;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View viewCor;
        TextView textNome;
        TextView textValor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewCor = itemView.findViewById(R.id.view_cor_categoria);
            textNome = itemView.findViewById(R.id.text_nome_categoria);
            textValor = itemView.findViewById(R.id.text_valor_categoria);
        }
    }
}
