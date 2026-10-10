package com.example.cofree.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.cofree.activities.Home;
import com.example.cofree.activities.Menu;
import com.example.cofree.R;
import com.example.cofree.activities.Transactions;

public class BottomTabBar extends LinearLayout {

    ImageButton tabMenu;

    ImageButton tabHome;

    ImageButton tabTransactions;


    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomTabBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context)
                .inflate(R.layout.bottom_tab_bar, this, true);

        tabMenu = findViewById(R.id.tab_menu);
        tabHome = findViewById(R.id.tab_home);
        tabTransactions = findViewById(R.id.tab_transactions);


        tabMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Menu.class);

            getContext().startActivity(intent);
        });
        tabHome.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Home.class);

            getContext().startActivity(intent);
        });
        tabTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Transactions.class);

            getContext().startActivity(intent);
        });

    }
}

