package com.example.cofree.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cofree.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Invoice extends AppCompatActivity {

    ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.button_pagar_fatura)
                .setOnClickListener(v -> abrirDialogPagamento());
    }

    private void abrirDialogPagamento() {
        View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_pagar_fatura, null);

        TextView valorFatura =
                dialogView.findViewById(R.id.dialog_valor_fatura);

        valorFatura.setText("R$ 1.284,90");

        androidx.appcompat.app.AlertDialog dialog =
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Pagar fatura")
                        .setView(dialogView)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Confirmar pagamento", null)
                        .create();

        dialog.setOnShowListener(ignored -> {
            // Fundo arredondado com a cor do tema.
            android.graphics.drawable.Drawable fundo =
                    androidx.appcompat.content.res.AppCompatResources.getDrawable(
                            this,
                            R.drawable.bg_dialog_cofree
                    );

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(fundo);
            }

            // Botão secundário.
            android.widget.Button cancelar = dialog.getButton(
                    androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE
            );

            cancelar.setTextColor(
                    androidx.core.content.ContextCompat.getColor(
                            this,
                            R.color.text_secondary
                    )
            );

            // Botão principal.
            android.widget.Button confirmar = dialog.getButton(
                    androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE
            );

            confirmar.setTextColor(
                    androidx.core.content.ContextCompat.getColor(
                            this,
                            R.color.text_primary
                    )
            );

            confirmar.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            androidx.core.content.ContextCompat.getColor(
                                    this,
                                    R.color.green_cofree
                            )
                    )
            );
        });

        dialog.show();
    }
}