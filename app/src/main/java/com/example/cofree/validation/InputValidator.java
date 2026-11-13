package com.example.cofree.validation;

import android.util.Patterns;

import com.example.cofree.ui.Input;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class InputValidator {

    private InputValidator() {
        // Evita instanciação da classe.
    }

    public static boolean required(Input input, String fieldName) {

        String value = input.getValue().trim();

        if (value.isEmpty()) {
            input.setError(fieldName + " é obrigatório.");
            return false;
        }

        input.clearError();
        return true;
    }

    public static boolean email(Input input) {

        String value = input.getValue().trim();

        if (value.isEmpty()) {
            input.setError("E-mail é obrigatório.");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            input.setError("Informe um e-mail válido.");
            return false;
        }

        input.clearError();
        return true;
    }

    public static boolean minLength(
            Input input,
            String fieldName,
            int minLength
    ) {

        String value = input.getValue();

        if (value.trim().isEmpty()) {
            input.setError(fieldName + " é obrigatório.");
            return false;
        }

        if (value.length() < minLength) {
            input.setError(
                    fieldName + " deve ter pelo menos "
                            + minLength + " caracteres."
            );
            return false;
        }

        input.clearError();
        return true;
    }

    public static boolean positiveNumber(
            Input input,
            String fieldName
    ) {

        String value = input.getValue().trim();

        if (value.isEmpty()) {
            input.setError(fieldName + " é obrigatório.");
            return false;
        }

        try {
            BigDecimal number = new BigDecimal(
                    value.replace(",", ".")
            );

            if (number.compareTo(BigDecimal.ZERO) <= 0) {
                input.setError(
                        fieldName + " deve ser maior que zero."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            input.setError("Informe um valor numérico válido.");
            return false;
        }

        input.clearError();
        return true;
    }

    public static boolean nonNegativeNumber(
            Input input,
            String fieldName
    ) {

        String value = input.getValue().trim();

        if (value.isEmpty()) {
            input.setError(fieldName + " é obrigatório.");
            return false;
        }

        try {
            BigDecimal number = new BigDecimal(
                    value.replace(",", ".")
            );

            if (number.compareTo(BigDecimal.ZERO) < 0) {
                input.setError(
                        fieldName + " não pode ser negativo."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            input.setError("Informe um valor numérico válido.");
            return false;
        }

        input.clearError();
        return true;
    }

    public static boolean date(Input input, String fieldName) {

        String value = input.getValue().trim();

        if (value.isEmpty()) {
            input.setError(fieldName + " é obrigatória.");
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate date = LocalDate.parse(value, formatter);

            if (date.isAfter(LocalDate.now())) {
                input.setError("A data não pode ser futura.");
                return false;
            }

        } catch (Exception e) {
            input.setError("Informe uma data válida (dd/MM/aaaa).");
            return false;
        }

        input.clearError();
        return true;
    }
}
