package com.example.cofree.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cofree.R;

public class Input extends LinearLayout {
    private TextView label;
    private EditText input;

    private ImageButton visibilityButton;

    public Input(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(
                R.layout.input,
                this,
                true
        );

        label = findViewById(R.id.input_label);
        input = findViewById(R.id.input_field);
        visibilityButton = findViewById(R.id.visibility_button);

        visibilityButton.setOnClickListener(v -> {
            if (input.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                visibilityButton.setImageResource(R.drawable.ic_visibility_off);
            } else {
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                visibilityButton.setImageResource(R.drawable.ic_visibility);
            }

            input.setSelection(input.getText().length());
        });

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Input);

        boolean showVisibility = attributes.getBoolean(R.styleable.Input_showVisibility, false);


        String labelText = attributes.getString(R.styleable.Input_label);

        String hintText = attributes.getString(R.styleable.Input_hint);

        attributes.recycle();

        // Se tá visível, tá visível
        visibilityButton.setVisibility(showVisibility ? VISIBLE : GONE);

        if (showVisibility) {
            input.setInputType(
                    InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD
            );
        }

        //Se não recebe label, não mostra label
        if (labelText == null || labelText.isEmpty()) {
            label.setVisibility(GONE);
        } else {
            label.setText(labelText);
        }

        input.setHint(hintText);
    }

}
