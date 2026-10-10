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
import com.example.cofree.adapter.ContaAdapter;
import com.example.cofree.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class Accounts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView buttonBack = findViewById(R.id.button_back);
        ImageView buttonAddAccount = findViewById(R.id.button_add_account);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, Account.class);
            startActivity(intent);
        });

        RecyclerView recyclerContas = findViewById(R.id.recycler_contas);


        List<Conta> contas = new ArrayList<>();

        contas.add(new Conta(
                "Carteira", 2500.00,
                R.drawable.ic_wallet, "#E4F824"
        ));

        contas.add(new Conta(
                "Conta corrente", 1250.50,
                R.drawable.ic_account_balance, "#2196F3"
        ));

        ContaAdapter adapter = new ContaAdapter(this, contas);

        recyclerContas.setLayoutManager(new LinearLayoutManager(this));
        recyclerContas.setAdapter(adapter);

    }
}