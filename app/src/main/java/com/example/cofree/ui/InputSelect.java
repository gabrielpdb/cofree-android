package com.example.cofree.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cofree.R;
import com.example.cofree.model.SelectOption;

public class InputSelect extends LinearLayout {

    private TextView label;
    private TextView value;
    private TextView rightInfo;

    private SelectOption[] options;
    private SelectOption selectedOption;

    public InputSelect(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(
                R.layout.input_select,
                this,
                true
        );

        label = findViewById(R.id.input_label);
        value = findViewById(R.id.input_value);
        rightInfo = findViewById(R.id.input_right_info);

        TypedArray attributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.InputSelect
        );

        String labelText = attributes.getString(
                R.styleable.InputSelect_label
        );

        String hintText = attributes.getString(
                R.styleable.InputSelect_hint
        );

        String rightInfoText = attributes.getString(R.styleable.InputSelect_rightInfo);

        attributes.recycle();

        if (labelText == null || labelText.isEmpty()) {
            label.setVisibility(GONE);
        } else {
            label.setText(labelText);
        }
        if (rightInfoText == null || rightInfoText.isEmpty()) {
            rightInfo.setVisibility(GONE);
        } else {
            rightInfo.setText(rightInfoText);
        }

        value.setHint(hintText);


    }

    public void setOptions(SelectOption[] options) {
        this.options = options;

        if (options.length > 0) {
            selectedOption = options[0];

            value.setText(selectedOption.getName());

            if (selectedOption.getRightInfo() != null) {
                rightInfo.setText(selectedOption.getRightInfo());
            }
        }

        setOnClickListener(v -> showOptions(options));
    }

    public void setSelectedId(int id) {
        if (options == null) {
            return;
        }

        for (SelectOption option : options) {
            if (option.getId() == id) {
                selectedOption = option;
                value.setText(option.getName());

                if (option.getRightInfo() != null) {
                    rightInfo.setText(option.getRightInfo());
                }

                break;
            }
        }
    }

    private void showOptions(SelectOption[] options) {

        String[] names = new String[options.length];

        for (int i = 0; i < options.length; i++) {
            names[i] = options[i].getName();
        }

        new AlertDialog.Builder(getContext())
                .setItems(names, (dialog, which) -> {
                    selectedOption = options[which];

                    value.setText(selectedOption.getName());

                    if (selectedOption.getRightInfo() != null) {
                        rightInfo.setText(selectedOption.getRightInfo());
                    }
                })
                .show();
    }

    public SelectOption getSelectedOption() {
        return selectedOption;
    }


}
