package com.example.cofree.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cofree.R;

public class InputTextArea extends LinearLayout {

    private TextView label;
    private EditText input;

    public InputTextArea(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(
                R.layout.input_textarea,
                this,
                true
        );

        label = findViewById(R.id.input_label);
        input = findViewById(R.id.input_field);

        TypedArray attributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.InputTextArea
        );

        String labelText = attributes.getString(
                R.styleable.InputTextArea_label
        );

        String hintText = attributes.getString(
                R.styleable.InputTextArea_hint
        );

        attributes.recycle();

        if (labelText == null || labelText.isEmpty()) {
            label.setVisibility(GONE);
        } else {
            label.setText(labelText);
        }

        input.setHint(hintText);
    }
}