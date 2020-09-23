package com.example.comercioemrede.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.comercioemrede.R;

public class TelaInicial extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


    }


    public void telaLogin(View view) {

        Switch swtLOJ = findViewById(R.id.swtLOJ);
        if(swtLOJ.isChecked()){
            Intent it = new Intent(TelaInicial.this, TelaLoginLOJ.class);
            startActivity(it);
        }else {
            Intent it = new Intent(TelaInicial.this, TelaLoginCLI.class);
            startActivity(it);
        }


    }
    public void telaCadastro(View view) {

        Switch swtLOJ = findViewById(R.id.swtLOJ);
        if(swtLOJ.isChecked()){
            Intent it = new Intent(TelaInicial.this, TelaCadastroLOJ.class);
            startActivity(it);
        }else {
            Intent it = new Intent(TelaInicial.this, TelaCadastroCLI.class);
            startActivity(it);
        }

    }




}
