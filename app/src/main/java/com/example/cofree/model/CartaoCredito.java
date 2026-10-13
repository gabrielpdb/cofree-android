package com.example.cofree.model;

public class CartaoCredito {

    private int id;
    private String nome;
    private String bandeira;
    private double limiteTotal;
    private double limiteUsado;

    public CartaoCredito(
            int id,
            String nome,
            String bandeira,
            double limiteTotal,
            double limiteUsado) {

        this.id = id;
        this.nome = nome;
        this.bandeira = bandeira;
        this.limiteTotal = limiteTotal;
        this.limiteUsado = limiteUsado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBandeira() {
        return bandeira;
    }

    public double getLimiteTotal() {
        return limiteTotal;
    }

    public double getLimiteUsado() {
        return limiteUsado;
    }

    public double getLimiteDisponivel() {
        return limiteTotal - limiteUsado;
    }

    public double getPercentualUtilizado() {
        if (limiteTotal <= 0) {
            return 0;
        }

        return limiteUsado / limiteTotal;
    }
}
