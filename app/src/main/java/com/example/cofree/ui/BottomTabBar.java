package com.example.cofree.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cofree.activities.CategoryReport;
import com.example.cofree.activities.CreditCardExpense;
import com.example.cofree.activities.Expense;
import com.example.cofree.activities.Home;
import com.example.cofree.activities.Income;
import com.example.cofree.activities.Menu;
import com.example.cofree.R;
import com.example.cofree.activities.PatrimonyEvolution;
import com.example.cofree.activities.Transactions;
import com.example.cofree.activities.Transfer;

public class BottomTabBar extends LinearLayout {

    ImageButton tabMenu;
    ImageButton tabReports;
    ImageButton tabHome;
    ImageButton tabTransactions;
    ImageButton tabAction;

    private View overlay;
    private View menuAcoes;
    private boolean menuAberto = false;

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
        tabReports = findViewById(R.id.tab_reports);
        tabHome = findViewById(R.id.tab_home);
        tabTransactions = findViewById(R.id.tab_transactions);


        tabMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Menu.class);

            getContext().startActivity(intent);
        });
        tabReports.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PatrimonyEvolution.class);

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

        tabAction = findViewById(R.id.tab_action);

        tabAction.setOnClickListener(v -> {
            if (menuAberto) {
                fecharMenuAcoes();
            } else {
                abrirMenuAcoes();
            }
        });

    }

    private void abrirMenuAcoes() {
        if (!(getContext() instanceof Activity)) return;

        Activity activity = (Activity) getContext();

        FrameLayout raiz = activity.findViewById(android.R.id.content);

        // Overlay escuro
        overlay = new View(getContext());
        overlay.setBackgroundColor(0x99000000);

        FrameLayout.LayoutParams overlayParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        raiz.addView(overlay, overlayParams);

        // Conteúdo do menu
        menuAcoes = LayoutInflater.from(getContext())
                .inflate(R.layout.menu_acoes, raiz, false);

        configurarAcoes();

        FrameLayout.LayoutParams menuParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM | Gravity.END
                );

        menuParams.rightMargin = dpToPx(8);
        menuParams.bottomMargin = getHeight() + dpToPx(24);

        raiz.addView(menuAcoes, menuParams);

        overlay.setOnClickListener(v -> fecharMenuAcoes());

        menuAberto = true;
    }

    private void fecharMenuAcoes() {
        if (overlay != null && overlay.getParent() != null) {
            ((ViewGroup) overlay.getParent()).removeView(overlay);
        }

        if (menuAcoes != null && menuAcoes.getParent() != null) {
            ((ViewGroup) menuAcoes.getParent()).removeView(menuAcoes);
        }

        overlay = null;
        menuAcoes = null;
        menuAberto = false;
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void configurarAcoes() {
        if (menuAcoes == null) return;

        menuAcoes.findViewById(R.id.acao_receita)
                .setOnClickListener(v -> selecionarAcao(Income.class));

        menuAcoes.findViewById(R.id.acao_despesa)
                .setOnClickListener(v -> selecionarAcao(Expense.class));

        menuAcoes.findViewById(R.id.acao_credito)
                .setOnClickListener(v -> selecionarAcao(CreditCardExpense.class));

        menuAcoes.findViewById(R.id.acao_transferencia)
                .setOnClickListener(v -> selecionarAcao(Transfer.class));
    }

    private void selecionarAcao(Class<?> activityClass) {
        fecharMenuAcoes();

        Intent intent = new Intent(getContext(), activityClass);
        getContext().startActivity(intent);
    }

}

