package com.example.cofree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;

public class Menu extends AppCompatActivity {

    LinearLayout buttonAccounts;
    LinearLayout buttonCategories;
    LinearLayout buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAccounts = findViewById(R.id.button_accounts);

        buttonAccounts.setOnClickListener(v -> {
            Intent intent = new Intent(this, Accounts.class);

            startActivity(intent);
        });

        buttonCategories = findViewById(R.id.button_categories);

        buttonCategories.setOnClickListener(v -> {
            Intent intent = new Intent(this, Categories.class);

            startActivity(intent);
        });

        buttonLogout = findViewById(R.id.button_logout);

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });
    }
}