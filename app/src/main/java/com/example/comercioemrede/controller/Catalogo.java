package com.example.comercioemrede.controller;

import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Catalogo implements Serializable {

    private String keyProduto;
    private String nome;
    private String tipo;
    private String precoMask;
    private String preco;
    private String oferta;
    private String Descricao;
    private String validadeOferta;
    private String tempoOferta;
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
    public void remover(){

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("catalogo")
                .child(idUsuario)
                .child(getKeyProduto());

        databaseReference.removeValue();
        removerProdutoPublico();
    }

    public void removerProdutoPublico(){

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase()
                .child("catalogo_publico")
                .child(getTipo())
                .child(getKeyProduto());

        databaseReference.removeValue();
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

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getTempoOferta() {
        return tempoOferta;
    }

    public void setTempoOferta(String tempoOferta) {
        this.tempoOferta = tempoOferta;
    }

    public String getPrecoMask() {
        return precoMask;
    }

    public void setPrecoMask(String precoMask) {
        this.precoMask = precoMask;
    }
}
