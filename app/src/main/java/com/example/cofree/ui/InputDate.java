package com.example.cofree.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cofree.R;

import java.util.Calendar;

public class InputDate extends LinearLayout {

    private TextView label;
    private EditText input;

    public InputDate(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.input_date, this, true);

        label = findViewById(R.id.input_label);
        input = findViewById(R.id.input_field);

        input.setFocusable(false);
        input.setClickable(true);

        input.setOnClickListener(v -> showDatePicker(context));

        setCurrentDate();

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.InputDate);

        String labelText = attributes.getString(
                R.styleable.InputDate_label
        );

        String hintText = attributes.getString(
                R.styleable.InputDate_hint
        );

        attributes.recycle();

        // Se tem label, mostra label
        if (labelText == null || labelText.isEmpty()) {
            label.setVisibility(GONE);
        } else {
            label.setText(labelText);
        }

        input.setHint(hintText);


    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        String date = String.format(
                "%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );

        input.setText(date);
    }

    private void showDatePicker(Context context) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                new ContextThemeWrapper(context, R.style.Theme_Cofree),
                (view, year, month, dayOfMonth) -> {
                    String date = String.format(
                            "%02d/%02d/%04d",
                            dayOfMonth,
                            month + 1,
                            year
                    );

                    input.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

}
