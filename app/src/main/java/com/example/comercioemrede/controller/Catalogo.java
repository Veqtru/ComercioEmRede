package com.example.comercioemrede.controller;

import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Catalogo {

    private String keyProduto;
    private String nome;
    private String tipo;
    private String preco;
    private String urlImage;
    private String oferta;
    private String validadeOferta;
    private List<String> fotos;

    public Catalogo(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("catalogo");
        setKeyProduto(databaseReference.push().getKey());

    }
    public void salvar(){

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("catalogo");
        databaseReference.child(idUsuario)
                .child(getKeyProduto())
                .setValue(this);

        salvarProdutoPublico();
    }
    public void salvarProdutoPublico(){

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("catalogo_publico");
        databaseReference.child(getTipo())
                .child(getKeyProduto())
                .setValue(this);
    }

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

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getValidadeOferta() {
        return validadeOferta;
    }

    public void setValidadeOferta(String validadeOferta) {
        this.validadeOferta = validadeOferta;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
