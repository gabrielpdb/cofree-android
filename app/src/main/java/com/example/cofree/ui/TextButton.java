package com.example.cofree.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.cofree.R;

public class TextButton extends LinearLayout {
    private Button button;

    public TextButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(
                R.layout.text_button,
                this,
                true
        );

        button = findViewById(R.id.text_button);

        button.setOnClickListener(v -> performClick());

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TextButton);

        String text = attributes.getString(R.styleable.TextButton_text);
        String type = attributes.getString(R.styleable.TextButton_type);


        attributes.recycle();


        button.setText(text);

        if ("red".equals(type)) {
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(context, R.color.expense)
            );
            button.setTextColor(
                    ContextCompat.getColorStateList(context, R.color.white)
            );
        } else {
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(context, R.color.green_cofree)
            );
            button.setTextColor(
                    ContextCompat.getColorStateList(context, R.color.black)
            );
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

}
