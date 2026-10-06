package com.example.cofree.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.cofree.R;

public class InputColor extends LinearLayout {
    private TextView label;
    private View colorView;

    public InputColor(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.input_color, this, true);

        label = findViewById(R.id.input_label);
        colorView = findViewById(R.id.input_color);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.InputColor);

        String labelText = attributes.getString(R.styleable.InputColor_label);

        attributes.recycle();


        if (labelText == null || labelText.isEmpty()) {
            label.setVisibility(GONE);
        } else {
            label.setText(labelText);
        }

        colorView.setBackgroundColor(Color.WHITE);

        colorView.setOnClickListener(v -> showColorPicker());
    }

    private void showColorPicker() {

        String[] colors = {
                "#F44336",
                "#E91E63",
                "#9C27B0",
                "#673AB7",
                "#3F51B5",
                "#2196F3",
                "#03A9F4",
                "#00BCD4",
                "#009688",
                "#4CAF50",
                "#8BC34A",
                "#CDDC39",
                "#E4F824",
                "#FFEB3B",
                "#FFC107",
                "#FF9800",
                "#FF5722",
                "#795548",
                "#9E9E9E",
                "#607D8B",
                "#800000",
                "#FF69B4",
                "#FF1493",
                "#7B1FA2",
                "#00FF00",
                "#00FFFF",
                "#0000FF",
                "#FF00FF",
                "#FFA500",
                "#008000"
        };

        LinearLayout list = new LinearLayout(getContext());
        list.setOrientation(LinearLayout.VERTICAL);
        list.setPadding(24, 16, 24, 16);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Escolha uma cor")
                .setView(list)
                .setNegativeButton("Cancelar", null)
                .create();

        for (String color : colors) {

            LinearLayout item = new LinearLayout(getContext());
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams itemParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            72
                    );

            item.setLayoutParams(itemParams);

            View circle = new View(getContext());

            LinearLayout.LayoutParams circleParams =
                    new LinearLayout.LayoutParams(64, 64);

            circleParams.setMargins(0, 0, 16, 0);

            circle.setLayoutParams(circleParams);

            GradientDrawable background = new GradientDrawable();
            background.setShape(GradientDrawable.OVAL);
            background.setColor(Color.parseColor(color));

            circle.setBackground(background);

            TextView hex = new TextView(getContext());
            hex.setText(color);
            hex.setTextSize(16);
            hex.setTextColor(
                    getResources().getColor(R.color.text_primary)
            );

            item.addView(circle);
            item.addView(hex);

            item.setOnClickListener(v -> {
                colorView.setBackgroundColor(
                        Color.parseColor(color)
                );

                dialog.dismiss();
            });

            list.addView(item);
        }

        dialog.show();
    }

}
