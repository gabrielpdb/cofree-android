package com.example.cofree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.model.ItemListaMovimentacao;
import com.example.cofree.model.Movimentacao;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CreditCardExpenseAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TIPO_DATA = 0;
    private static final int TIPO_MOVIMENTACAO = 1;

    private final Context context;
    private final List<ItemListaMovimentacao> itens;

    public CreditCardExpenseAdapter(
            Context context,
            List<ItemListaMovimentacao> itens) {
        this.context = context;
        this.itens = itens;
    }

    @Override
    public int getItemViewType(int position) {
        return itens.get(position).getTipoItem()
                == ItemListaMovimentacao.TipoItem.DATA
                ? TIPO_DATA
                : TIPO_MOVIMENTACAO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());

        if (viewType == TIPO_DATA) {
            View view = inflater.inflate(
                    R.layout.item_data_movimentacao,
                    parent,
                    false
            );
            return new DataViewHolder(view);
        }

        View view = inflater.inflate(
                R.layout.item_movimentacao,
                parent,
                false
        );

        return new MovimentacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position) {

        ItemListaMovimentacao item = itens.get(position);

        if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).textData.setText(item.getData());
            return;
        }

        MovimentacaoViewHolder vh = (MovimentacaoViewHolder) holder;
        Movimentacao movimentacao = item.getMovimentacao();

        vh.textTitulo.setText(movimentacao.getTitulo());
        vh.textDetalhe.setText(movimentacao.getCategoria());

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        vh.textValor.setText(formatoMoeda.format(movimentacao.getValor()));
        vh.textValor.setTextColor(
                ContextCompat.getColor(context, R.color.credit)
        );

        vh.imageTipo.setImageResource(R.drawable.ic_credit_card);
        vh.imageTipo.setColorFilter(
                ContextCompat.getColor(context, R.color.credit)
        );

        String detalhe = movimentacao.getCategoria();

        if (detalhe == null || detalhe.isEmpty()) {
            vh.textDetalhe.setVisibility(View.GONE);
        } else {
            vh.textDetalhe.setVisibility(View.VISIBLE);
            vh.textDetalhe.setText(detalhe);
        }

        String secundario = movimentacao.getDetalhe();

        if (secundario == null || secundario.isEmpty()) {
            vh.textValorSecundario.setVisibility(View.GONE);
        } else {
            vh.textValorSecundario.setVisibility(View.VISIBLE);
            vh.textValorSecundario.setText(secundario);
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textData;

        DataViewHolder(@NonNull View itemView) {
            super(itemView);
            textData = itemView.findViewById(
                    R.id.text_data_movimentacao
            );
        }
    }

    static class MovimentacaoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageTipo;
        TextView textTitulo;
        TextView textDetalhe;
        TextView textValor;
        TextView textValorSecundario;

        MovimentacaoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTipo = itemView.findViewById(
                    R.id.image_tipo_movimentacao
            );
            textTitulo = itemView.findViewById(
                    R.id.text_titulo_movimentacao
            );
            textDetalhe = itemView.findViewById(
                    R.id.text_detalhe_movimentacao
            );
            textValor = itemView.findViewById(
                    R.id.text_valor_movimentacao
            );
            textValorSecundario = itemView.findViewById(
                    R.id.text_valor_secundario
            );
        }
    }
}
