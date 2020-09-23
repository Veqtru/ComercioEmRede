package com.example.comercioemrede.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comercioemrede.R;

public class RedirectCadastro extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect_cadastro);
    }

    public void telaCadastroCLI(View view) {
        Intent it = new Intent(RedirectCadastro.this, TelaCadastroCLI.class);
        startActivity(it);
    }

    public void telaCadastroLOJ(View view) {
        Intent it = new Intent(RedirectCadastro.this, TelaCadastroLOJ.class);
        startActivity(it);
    }
    public void redirectLogin(View view){
        Intent it = new Intent(RedirectCadastro.this, RedirectLogin.class);
        startActivity(it);
    }
}