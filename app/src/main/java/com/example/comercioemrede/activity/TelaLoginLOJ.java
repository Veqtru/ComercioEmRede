package com.example.comercioemrede.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comercioemrede.R;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.example.comercioemrede.controller.Lojista;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLoginLOJ extends AppCompatActivity {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button bttnLogin;

    private Lojista lojista;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_loj);

        inicializarComponentes();

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailLOJ = edtEmail.getText().toString();
                String senhaLOJ = edtSenha.getText().toString();

                if ( !emailLOJ.isEmpty() ){
                    if ( !senhaLOJ.isEmpty() ){

                        lojista = new Lojista();
                        lojista.setEmail( emailLOJ );
                        lojista.setSenha( senhaLOJ );
                        validarLogin(lojista);



                    }else{
                        Toast.makeText(TelaLoginLOJ.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT).show();
                    }



                }else{
                    Toast.makeText(TelaLoginLOJ.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarLogin( Lojista lojista ){

        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();


        autenticacao.signInWithEmailAndPassword(
                lojista.getEmail(),
                lojista.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    startActivity(new Intent(TelaLoginLOJ.this, TelaPrincipal.class));
                    finish();

                }else{

                    Toast.makeText(TelaLoginLOJ.this,
                            "Email ou senha incorreta(o) " ,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void recuperarSenha(View view){
        Intent i = new Intent(TelaLoginLOJ.this, TelaRecuperarSenha.class);
        startActivity( i );
    }

    public void abrirCadastroLOJ(View view){
        Intent i = new Intent(TelaLoginLOJ.this, TelaCadastroLOJ.class);
        startActivity( i );
    }

    public void inicializarComponentes() {
        edtEmail = findViewById(R.id.emailLoginLOJ);
        edtSenha = findViewById(R.id.senhaLoginLOJ);
        bttnLogin = findViewById(R.id.bttnLoginLOJ);
    }
}
