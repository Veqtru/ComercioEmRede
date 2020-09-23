package com.example.comercioemrede.model;

import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Lojista extends Usuario {

    public Lojista(String _cod_usu, String _nome, String _email, String _senha, String _telefone){
        super(_cod_usu, _nome, _email, _senha, _telefone);
    }

    private String cnpj;
    private String endereco;
    private String telefone;

    public Lojista() {
    }

    public void salvarLojista(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child("lojista").child(getCod_usu());
        usuariosRef.setValue(this);
    }


    public Lojista(String cnpj, String endereco){
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone=telefone;
    }

    @Override
    public String toString() {
        return "Lojista{" +
                "cnpj='" + cnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
