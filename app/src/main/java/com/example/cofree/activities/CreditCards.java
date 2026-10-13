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
import com.example.cofree.adapter.CartaoCreditoAdapter;
import com.example.cofree.adapter.ContaAdapter;
import com.example.cofree.model.CartaoCredito;
import com.example.cofree.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class CreditCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credit_cards);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView buttonBack = findViewById(R.id.button_back);
        ImageView buttonAddCreditCard = findViewById(R.id.button_add_card);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonAddCreditCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreditCard.class);
            startActivity(intent);
        });

        RecyclerView recyclerCartoes = findViewById(R.id.recycler_cartoes);

        List<CartaoCredito> cartoes = new ArrayList<>();

        cartoes.add(new CartaoCredito(
                1, "Sicredi", "VISA", 4000.00, 3345.15
        ));

        cartoes.add(new CartaoCredito(
                2, "Banrisul", "VISA", 4000.00, 3345.15
        ));

        CartaoCreditoAdapter adapter =
                new CartaoCreditoAdapter(this, cartoes);

        recyclerCartoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerCartoes.setAdapter(adapter);

    }
}