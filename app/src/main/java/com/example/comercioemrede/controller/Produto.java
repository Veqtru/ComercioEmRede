package com.example.comercioemrede.controller;

public class Produto {
    private Integer cod_pro;
    private String nome;
    private String tipo;
    private Float valor;
    private String foto;

    public Produto(Integer cod_pro, String nome, String tipo, Float preco, String imagem) {
        this.cod_pro = cod_pro;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = preco;
        this.foto = imagem;

    }

    public Produto() {

    }

    @Override
    public String toString() {
        return "Produto{" +
                "cod_pro=" + cod_pro +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", preco=" + valor +
                ", imagem='" + foto + '\'' +
                '}';
    }

    public Integer getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(Integer cod_pro) {
        this.cod_pro = cod_pro;
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

    public Float getPreco() {
        return valor;
    }

    public void setPreco(Float preco) {
        this.valor = preco;
    }

    public String getImagem() {
        return foto;
    }

    public void setImagem(String imagem) {
        this.foto = imagem;
    }

    public void setCod_pro() {
        cod_pro ++;
    }

    public void setImagem() {
        foto = null;
    }
}
