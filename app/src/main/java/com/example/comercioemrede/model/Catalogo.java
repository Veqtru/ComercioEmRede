package com.example.comercioemrede.model;

public class Catalogo {

    private String keyProduto;
    private String nome;
    private String tipo;
    private String quantidade;
    private String preco;
    private String urlImage;

    public String getKeyProduto() {
        return keyProduto;
    }

    public void setKeyProduto(String keyProduto) {
        this.keyProduto = keyProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
