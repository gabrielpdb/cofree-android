package com.example.cofree.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.model.Categoria;

import java.util.List;

public class CategoriaAdapter
        extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private final List<Categoria> categorias;

    public CategoriaAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);

        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CategoriaViewHolder holder, int position) {

        Categoria categoria = categorias.get(position);

        holder.textNome.setText(categoria.getNome());

        try {
            holder.viewCor.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor(categoria.getCor())
                    )
            );
        } catch (IllegalArgumentException e) {
            holder.viewCor.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.GRAY
                    )
            );
        }
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView textNome;
        View viewCor;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.text_nome_categoria);
            viewCor = itemView.findViewById(R.id.view_cor_categoria);
        }
    }
}
