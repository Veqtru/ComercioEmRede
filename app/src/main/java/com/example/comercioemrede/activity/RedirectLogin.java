package com.example.comercioemrede.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comercioemrede.R;

public class RedirectLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect_login);
    }
    public void telaLoginCLI(View view){
        Intent it = new Intent(RedirectLogin.this, TelaLoginCLI.class);
        startActivity(it);
    }
    public void telaLoginLOJ(View view){
        Intent it = new Intent(RedirectLogin.this, TelaLoginLOJ.class);
        startActivity(it);
    }
    public void redirectCadastro(View view){
        Intent it = new Intent(RedirectLogin.this, RedirectCadastro.class);
        startActivity(it);
    }


}
