package com.example.comercioemrede.model;

import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Cliente extends Usuario {

    public Cliente(String _cod_usu, String _nome, String _email, String _senha, String _telefone){
        super(_cod_usu, _nome, _email, _senha, _telefone);
    }

    private String cpf;
    private String localizacao;


    public Cliente() {
    }

    public void salvarCliente(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child("cliente").child(getCod_usu());
        usuariosRef.setValue(this);
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
