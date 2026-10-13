package com.example.cofree.model;

public class ItemCategoriaRelatorio {

    private final String nome;
    private final String cor;
    private final double valor;

    public ItemCategoriaRelatorio(
            String nome,
            String cor,
            double valor) {

        this.nome = nome;
        this.cor = cor;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public double getValor() {
        return valor;
    }
}
