package com.example.comercioemrede.controller;

import com.google.firebase.database.Exclude;

public class Usuario {

    private String cod_usu;
    private String nome;
    private String email;
    private String senha;


    public Usuario() {
    }


    public Usuario(String cod_usu, String nome, String email, String senha, String telefone){
            this.cod_usu =cod_usu;
            this.nome=nome;
            this.senha=senha;
            this.email=email;

}

    @Override
    public String toString() {
        return "Usuario{" +
                "cod_usu=" + cod_usu +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public String getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(String cod_usu) {
        this.cod_usu = cod_usu;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}