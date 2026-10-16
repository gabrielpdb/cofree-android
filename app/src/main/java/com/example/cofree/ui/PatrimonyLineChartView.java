package com.example.cofree.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.cofree.R;
import com.example.cofree.model.PatrimonyDataPoint;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PatrimonyLineChartView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final List<PatrimonyDataPoint> dados = new ArrayList<>();

    private final int corLinha = R.color.income;
    private final int corGrade = 0xFF555258;
    private final int corTexto = 0xFFB0ADB2;

    private final NumberFormat formatoMoeda =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public PatrimonyLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configurar();
    }

    public PatrimonyLineChartView(Context context) {
        super(context);
        configurar();
    }

    private void configurar() {
        paint.setStrokeWidth(dp(1));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void atualizarDados(List<PatrimonyDataPoint> novosDados) {
        dados.clear();

        if (novosDados != null) {
            dados.addAll(novosDados);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(
                androidx.core.content.ContextCompat.getColor(
                        getContext(),
                        R.color.medium
                )
        );

        paint.setStyle(Paint.Style.FILL);

        android.graphics.RectF fundo =
                new android.graphics.RectF(0, 0, getWidth(), getHeight());

        canvas.drawRoundRect(fundo, dp(12), dp(12), paint);

        if (dados.size() < 2) {
            return;
        }

        float largura = getWidth();
        float altura = getHeight();

        float margemEsquerda = dp(32);
        float margemDireita = dp(16);
        float margemSuperior = dp(20);
        float margemInferior = dp(36);

        float graficoLargura = largura - margemEsquerda - margemDireita;
        float graficoAltura = altura - margemSuperior - margemInferior;

        if (graficoLargura <= 0 || graficoAltura <= 0) {
            return;
        }

        double minimo = Double.MAX_VALUE;
        double maximo = -Double.MAX_VALUE;

        for (PatrimonyDataPoint ponto : dados) {
            minimo = Math.min(minimo, ponto.getValor());
            maximo = Math.max(maximo, ponto.getValor());
        }

        if (minimo == maximo) {
            double margem = Math.max(Math.abs(maximo) * 0.1, 1);
            minimo -= margem;
            maximo += margem;
        }

        double intervalo = maximo - minimo;
        minimo -= intervalo * 0.1;
        maximo += intervalo * 0.1;

        // Linhas de grade e valores do eixo vertical.
        paint.setColor(corGrade);
        paint.setStrokeWidth(dp(1));
        paint.setStyle(Paint.Style.STROKE);

        paint.setTextSize(sp(11));
        paint.setTypeface(android.graphics.Typeface.DEFAULT);
        paint.setTextAlign(Paint.Align.RIGHT);

        int quantidadeLinhas = 4;

        for (int i = 0; i <= quantidadeLinhas; i++) {
            float y = margemSuperior
                    + graficoAltura * i / quantidadeLinhas;

            canvas.drawLine(
                    margemEsquerda,
                    y,
                    largura - margemDireita,
                    y,
                    paint
            );

            double valorEixo = maximo
                    - (maximo - minimo) * i / quantidadeLinhas;

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(corTexto);

            canvas.drawText(
                    formatarEixo(valorEixo),
                    margemEsquerda - dp(8),
                    y + dp(4),
                    paint
            );

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(corGrade);
        }

        // Pontos e linha do patrimônio.
        Path caminho = new Path();

        for (int i = 0; i < dados.size(); i++) {
            float x = margemEsquerda
                    + graficoLargura * i / (dados.size() - 1);

            float y = margemSuperior
                    + (float) ((maximo - dados.get(i).getValor())
                    / (maximo - minimo)) * graficoAltura;

            if (i == 0) {
                caminho.moveTo(x, y);
            } else {
                caminho.lineTo(x, y);
            }
        }

        paint.setColor(
                androidx.core.content.ContextCompat.getColor(
                        getContext(),
                        R.color.income
                )
        );
        paint.setStrokeWidth(dp(2.5f));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(caminho, paint);

        // Marcadores e rótulos do eixo horizontal.
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(sp(11));

        float larguraRotulo = dp(48);

        int intervaloRotulos = Math.max(
                1,
                (int) Math.ceil(larguraRotulo
                        / (graficoLargura / (dados.size() - 1)))
        );

        for (int i = 0; i < dados.size(); i++) {
            float x = margemEsquerda
                    + graficoLargura * i / (dados.size() - 1);

            float y = margemSuperior
                    + (float) ((maximo - dados.get(i).getValor())
                    / (maximo - minimo)) * graficoAltura;

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(
                    androidx.core.content.ContextCompat.getColor(
                            getContext(),
                            R.color.income
                    )
            );
            canvas.drawCircle(x, y, dp(4), paint);

            if (i % intervaloRotulos == 0 || i == dados.size() - 1) {
                paint.setColor(corTexto);

                canvas.drawText(
                        dados.get(i).getRotulo(),
                        x,
                        altura - dp(10),
                        paint
                );
            }
        }
    }

    private String formatarEixo(double valor) {
        double absoluto = Math.abs(valor);

        if (absoluto >= 1000000) {
            return String.format(
                    Locale.forLanguageTag("pt-BR"),
                    "%.1f mi",
                    valor / 1000000
            );
        }

        if (absoluto >= 1000) {
            return String.format(
                    Locale.forLanguageTag("pt-BR"),
                    "%.0f mil",
                    valor / 1000
            );
        }

        return String.format(
                Locale.forLanguageTag("pt-BR"),
                "%.0f",
                valor
        );
    }

    private float dp(float valor) {
        return valor * getResources().getDisplayMetrics().density;
    }

    private float sp(float valor) {
        return valor * getResources().getDisplayMetrics().scaledDensity;
    }
}
