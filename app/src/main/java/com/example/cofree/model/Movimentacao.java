package com.example.cofree.model;

public class Movimentacao {

    private Tipo tipo;
    private String titulo;
    private double valor;
    private String data;
    private String categoria;
    private String conta;
    private String contaDestino;
    private String detalhe;
    private String valorSecundario;
    public Movimentacao(
            Tipo tipo,
            String titulo,
            double valor,
            String data,
            String categoria,
            String conta,
            String contaDestino,
            String detalhe,
            String valorSecundario) {

        this.tipo = tipo;
        this.titulo = titulo;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.conta = conta;
        this.contaDestino = contaDestino;
        this.detalhe = detalhe;
        this.valorSecundario = valorSecundario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getConta() {
        return conta;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getValorSecundario() {
        return valorSecundario;
    }

    public enum Tipo {
        RECEITA,
        DESPESA,
        TRANSFERENCIA,
        CREDITO,
        RENDIMENTO,
        ALTERACAO
    }
}
