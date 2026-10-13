package com.example.cofree.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DonutChartView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int[] cores = {
            0xFF00C5D4,
            0xFF804526,
            0xFFCC0000
    };

    private String[] categorias = {
            "Aluguel",
            "Gasolina",
            "Tigrinho"
    };

    private double[] valores = {
            600.00,
            1200.00,
            3511.50
    };

    private double total = 5311.50;

    public DonutChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configurar();
    }

    public DonutChartView(Context context) {
        super(context);
        configurar();
    }

    private void configurar() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(36));
        paint.setStrokeCap(Paint.Cap.BUTT);

        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(sp(16));
        textPaint.setTypeface(
                android.graphics.Typeface.create(
                        "sans-serif", android.graphics.Typeface.BOLD));
    }

    public void atualizarDados(
            String[] novasCategorias,
            double[] novosValores,
            int[] novasCores
    ) {
        if (novasCategorias == null
                || novosValores == null
                || novasCores == null
                || novasCategorias.length != novosValores.length
                || novasCategorias.length != novasCores.length) {
            return;
        }

        categorias = novasCategorias.clone();
        valores = novosValores.clone();
        cores = novasCores.clone();

        total = 0;

        for (double valor : valores) {
            total += valor;
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (total <= 0 || valores.length == 0) {
            return;
        }

        float centroX = getWidth() / 2f;
        float centroY = getHeight() / 2f;
        float raio = Math.min(getWidth(), getHeight()) * 0.32f;

        RectF area = new RectF(
                centroX - raio,
                centroY - raio,
                centroX + raio,
                centroY + raio
        );

        float anguloInicial = -90f;
        float espaco = 3f;

        for (int i = 0; i < valores.length; i++) {
            float angulo = (float) (valores[i] / total * 360);

            paint.setColor(cores[i]);

            canvas.drawArc(
                    area,
                    anguloInicial + espaco / 2,
                    Math.max(0, angulo - espaco),
                    false,
                    paint
            );

            anguloInicial += angulo;
        }

        float centroTextoY = centroY - (textPaint.ascent()
                + textPaint.descent()) / 2f;

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTypeface(
                android.graphics.Typeface.create(
                        "sans-serif",
                        android.graphics.Typeface.BOLD
                )
        );

        textPaint.setTextSize(sp(16));

        canvas.drawText(
                "Total",
                centroX,
                centroTextoY - dp(10),
                textPaint
        );

        textPaint.setTextSize(sp(14));

        canvas.drawText(
                formatarMoeda(total),
                centroX,
                centroTextoY + dp(12),
                textPaint
        );
    }

    private String formatarMoeda(double valor) {
        return String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "R$ %,.2f",
                        valor
                ).replace(',', '#')
                .replace('.', ',')
                .replace('#', '.');
    }

    private float dp(float valor) {
        return valor * getResources().getDisplayMetrics().density;
    }

    private float sp(float valor) {
        return valor * getResources().getDisplayMetrics().scaledDensity;
    }
}
