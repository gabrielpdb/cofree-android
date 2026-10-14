package com.example.cofree.model;

public class PatrimonyDataPoint {

    private final String rotulo;
    private final double valor;

    public PatrimonyDataPoint(String rotulo, double valor) {
        this.rotulo = rotulo;
        this.valor = valor;
    }

    public String getRotulo() {
        return rotulo;
    }

    public double getValor() {
        return valor;
    }
}
