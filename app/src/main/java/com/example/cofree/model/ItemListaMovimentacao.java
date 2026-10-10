package com.example.cofree.model;

public class ItemListaMovimentacao {

    private final TipoItem tipoItem;
    private final String data;
    private final Movimentacao movimentacao;
    public ItemListaMovimentacao(String data) {
        this.tipoItem = TipoItem.DATA;
        this.data = data;
        this.movimentacao = null;
    }

    public ItemListaMovimentacao(Movimentacao movimentacao) {
        this.tipoItem = TipoItem.MOVIMENTACAO;
        this.data = null;
        this.movimentacao = movimentacao;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public String getData() {
        return data;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public enum TipoItem {
        DATA,
        MOVIMENTACAO
    }
}
