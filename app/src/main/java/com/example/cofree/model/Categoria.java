package com.example.cofree.model;

public class Categoria {

    private int id;
    private String nome;
    private String cor;
    private TipoCategoria tipo;

    public Categoria(int id, String nome, String cor, TipoCategoria tipo) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public enum TipoCategoria {
        RECEITA, DESPESA
    }

}
