package com.example.cofree.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.activities.CreditCardExpense;
import com.example.cofree.activities.Expense;
import com.example.cofree.activities.Income;
import com.example.cofree.activities.Transfer;
import com.example.cofree.model.ItemListaMovimentacao;
import com.example.cofree.model.Movimentacao;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MovimentacaoAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TIPO_DATA = 0;
    private static final int TIPO_MOVIMENTACAO = 1;

    private final Context context;
    private final List<ItemListaMovimentacao> itens;

    public MovimentacaoAdapter(
            Context context,
            List<ItemListaMovimentacao> itens) {
        this.context = context;
        this.itens = itens;
    }

    @Override
    public int getItemViewType(int position) {
        return itens.get(position).getTipoItem()
                == ItemListaMovimentacao.TipoItem.DATA
                ? TIPO_DATA : TIPO_MOVIMENTACAO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());

        if (viewType == TIPO_DATA) {
            View view = inflater.inflate(
                    R.layout.item_data_movimentacao, parent, false);
            return new DataViewHolder(view);
        }

        View view = inflater.inflate(
                R.layout.item_movimentacao, parent, false);
        return new MovimentacaoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder, int position) {

        ItemListaMovimentacao item = itens.get(position);

        if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).textData.setText(item.getData());
            return;
        }

        MovimentacaoViewHolder movimentacaoHolder =
                (MovimentacaoViewHolder) holder;

        Movimentacao movimentacao = item.getMovimentacao();

        movimentacaoHolder.textTitulo.setText(movimentacao.getTitulo());

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        movimentacaoHolder.textValor.setText(
                formatoMoeda.format(movimentacao.getValor()));

        int icone;
        int cor;

        switch (movimentacao.getTipo()) {
            case RECEITA:
                icone = R.drawable.ic_income;
                cor = R.color.income;
                break;

            case DESPESA:
                icone = R.drawable.ic_expense;
                cor = R.color.expense;
                break;

            case TRANSFERENCIA:
                icone = R.drawable.ic_transfer;
                cor = R.color.transfer;
                break;

            case CREDITO:
                icone = R.drawable.ic_credit_card;
                cor = R.color.credit;
                break;

            case RENDIMENTO:
                icone = R.drawable.ic_income;
                cor = R.color.income;
                break;

            case ALTERACAO:
                icone = R.drawable.ic_transfer;
                cor = R.color.transfer;
                break;

            default:
                icone = R.drawable.ic_wallet;
                cor = R.color.text_secondary;
                break;
        }

        movimentacaoHolder.imageTipo.setImageResource(icone);

        movimentacaoHolder.imageTipo.setColorFilter(
                ContextCompat.getColor(context, cor));

        movimentacaoHolder.textValor.setTextColor(
                ContextCompat.getColor(context, cor));

        String detalhe = movimentacao.getCategoria();

        if (movimentacao.getTipo() == Movimentacao.Tipo.TRANSFERENCIA) {
            detalhe = movimentacao.getConta();

        } else if (
                movimentacao.getTipo() == Movimentacao.Tipo.RENDIMENTO
                        || movimentacao.getTipo() == Movimentacao.Tipo.ALTERACAO) {

            detalhe = movimentacao.getDetalhe();

        } else if (movimentacao.getTipo() == Movimentacao.Tipo.CREDITO) {
            detalhe = movimentacao.getConta();
        }

        if (detalhe == null || detalhe.isEmpty()) {
            movimentacaoHolder.textDetalhe.setVisibility(View.GONE);
        } else {
            movimentacaoHolder.textDetalhe.setVisibility(View.VISIBLE);
            movimentacaoHolder.textDetalhe.setText(detalhe);
        }

        String secundario = movimentacao.getValorSecundario();

        if (movimentacao.getTipo() == Movimentacao.Tipo.TRANSFERENCIA
                && movimentacao.getContaDestino() != null) {

            secundario = movimentacao.getContaDestino();

        } else if (movimentacao.getTipo() == Movimentacao.Tipo.CREDITO
                && movimentacao.getDetalhe() != null) {

            secundario = movimentacao.getDetalhe();
        }

        if (secundario == null || secundario.isEmpty()) {
            movimentacaoHolder.textValorSecundario.setVisibility(View.GONE);
        } else {
            movimentacaoHolder.textValorSecundario.setVisibility(View.VISIBLE);
            movimentacaoHolder.textValorSecundario.setText(secundario);
        }

        // Clique para abrir a tela de cadastro correspondente
        movimentacaoHolder.itemView.setOnClickListener(v -> {
            Class<?> activityClass;

            switch (movimentacao.getTipo()) {
                case RECEITA:
                    activityClass = Income.class;
                    break;

                case DESPESA:
                    activityClass = Expense.class;
                    break;
                case CREDITO:
                    activityClass = CreditCardExpense.class;
                    break;

                case TRANSFERENCIA:
                    activityClass = Transfer.class;
                    break;

                default:
                    return;
            }

            Intent intent = new Intent(v.getContext(), activityClass);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textData;

        DataViewHolder(@NonNull View itemView) {
            super(itemView);
            textData = itemView.findViewById(R.id.text_data_movimentacao);
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

            imageTipo = itemView.findViewById(R.id.image_tipo_movimentacao);
            textTitulo = itemView.findViewById(R.id.text_titulo_movimentacao);
            textDetalhe = itemView.findViewById(R.id.text_detalhe_movimentacao);
            textValor = itemView.findViewById(R.id.text_valor_movimentacao);
            textValorSecundario =
                    itemView.findViewById(R.id.text_valor_secundario);
        }
    }
}
