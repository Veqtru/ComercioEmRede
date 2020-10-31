package com.example.comercioemrede.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comercioemrede.R;

public class TelaInformacaoPoduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informacao_poduto);
    }
    public void localLoja(View view){
        Intent it = new Intent(TelaInformacaoPoduto.this, TelaLocal.class);
        startActivity(it);
    }
}
