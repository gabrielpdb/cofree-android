package com.example.cofree;

public class Conta {
    private String nome;
    private double saldo;
    private int icone;

    private String cor;

    public Conta(String nome, double saldo, int icone, String cor) {
        this.nome = nome;
        this.saldo = saldo;
        this.icone = icone;
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getIcone() {
        return icone;
    }

    public String getCor() {
        return cor;
    }
}
