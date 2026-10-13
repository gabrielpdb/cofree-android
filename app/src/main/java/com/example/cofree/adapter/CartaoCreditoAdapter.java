package com.example.cofree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cofree.R;
import com.example.cofree.model.CartaoCredito;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartaoCreditoAdapter
        extends RecyclerView.Adapter<CartaoCreditoAdapter.CartaoViewHolder> {

    private final Context context;
    private final List<CartaoCredito> cartoes;

    public CartaoCreditoAdapter(
            Context context,
            List<CartaoCredito> cartoes) {
        this.context = context;
        this.cartoes = cartoes;
    }

    @NonNull
    @Override
    public CartaoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cartao_credito, parent, false);

        return new CartaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CartaoViewHolder holder,
            int position) {

        CartaoCredito cartao = cartoes.get(position);

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        holder.textNome.setText(cartao.getNome());

        holder.textLimiteTotal.setText(
                formatoMoeda.format(cartao.getLimiteTotal()));

        holder.textLimiteUsado.setText(
                formatoMoeda.format(cartao.getLimiteUsado()));

        holder.textLimiteDisponivel.setText(
                formatoMoeda.format(cartao.getLimiteDisponivel()));

        // Bandeira do cartão
        int recursoBandeira = obterBandeira(cartao.getBandeira());

        if (recursoBandeira != 0) {
            holder.imageBandeira.setImageResource(recursoBandeira);
            holder.imageBandeira.setVisibility(View.VISIBLE);
        } else {
            holder.imageBandeira.setVisibility(View.INVISIBLE);
        }

        // Barra proporcional ao limite utilizado
        double limiteTotal = cartao.getLimiteTotal();
        double limiteUsado = cartao.getLimiteUsado();

        double percentualUsado = limiteTotal > 0
                ? Math.max(0, Math.min(1, limiteUsado / limiteTotal))
                : 0;

        double percentualDisponivel = 1 - percentualUsado;

        LinearLayout.LayoutParams usadoParams =
                (LinearLayout.LayoutParams)
                        holder.barraUsado.getLayoutParams();

        usadoParams.width = 0;
        usadoParams.weight = (float) percentualUsado;
        holder.barraUsado.setLayoutParams(usadoParams);

        LinearLayout.LayoutParams disponivelParams =
                (LinearLayout.LayoutParams)
                        holder.barraDisponivel.getLayoutParams();

        disponivelParams.width = 0;
        disponivelParams.weight = (float) percentualDisponivel;
        holder.barraDisponivel.setLayoutParams(disponivelParams);
    }

    private int obterBandeira(String bandeira) {
        if (bandeira == null) {
            return 0;
        }

        switch (bandeira.toUpperCase(Locale.ROOT)) {
            case "VISA":
                return R.drawable.ic_credit_card;

            case "MASTERCARD":
                return R.drawable.ic_credit_card;

            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return cartoes.size();
    }

    static class CartaoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageBandeira;
        TextView textNome;
        TextView textLimiteTotal;
        TextView textLimiteUsado;
        TextView textLimiteDisponivel;
        View barraUsado;
        View barraDisponivel;

        public CartaoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageBandeira = itemView.findViewById(R.id.image_bandeira);
            textNome = itemView.findViewById(R.id.text_nome_cartao);
            textLimiteTotal = itemView.findViewById(R.id.text_limite_total);
            textLimiteUsado = itemView.findViewById(R.id.text_limite_usado);
            textLimiteDisponivel =
                    itemView.findViewById(R.id.text_limite_disponivel);

            barraUsado = itemView.findViewById(R.id.barra_limite_usado);
            barraDisponivel =
                    itemView.findViewById(R.id.barra_limite_disponivel);
        }
    }
}
