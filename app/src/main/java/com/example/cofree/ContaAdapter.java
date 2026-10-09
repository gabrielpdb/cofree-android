package com.example.cofree;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ContaViewHolder> {

    private final Context context;
    private final List<Conta> contas;

    public ContaAdapter(Context context, List<Conta> contas) {
        this.context = context;
        this.contas = contas;
    }

    @NonNull
    @Override
    public ContaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conta, parent, false);

        return new ContaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContaViewHolder holder, int position) {
        Conta conta = contas.get(position);

        holder.textNome.setText(conta.getNome());
        holder.imageIcone.setImageResource(conta.getIcone());

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        holder.textSaldo.setText(formatoMoeda.format(conta.getSaldo()));

        int cor = conta.getSaldo() >= 0
                ? R.color.income
                : R.color.expense;

        holder.textSaldo.setTextColor(
                ContextCompat.getColor(context, cor)
        );

        holder.imageIcone.setColorFilter(
                Color.parseColor(conta.getCor())
        );
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    static class ContaViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIcone;
        TextView textNome;
        TextView textSaldo;

        public ContaViewHolder(@NonNull View itemView) {
            super(itemView);

            imageIcone = itemView.findViewById(R.id.image_icone_conta);
            textNome = itemView.findViewById(R.id.text_nome_conta);
            textSaldo = itemView.findViewById(R.id.text_saldo_conta);
        }
    }
}
