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
import com.example.comercioemrede.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLoginCLI extends AppCompatActivity {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button bttnLogin;

    private Cliente cliente;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_cli);

        inicializarComponentes();

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailCLI = edtEmail.getText().toString();
                String senhaCLI = edtSenha.getText().toString();

                if ( !emailCLI.isEmpty() ){
                    if ( !senhaCLI.isEmpty() ){

                        cliente = new Cliente();
                        cliente.setEmail( emailCLI );
                        cliente.setSenha( senhaCLI );
                        validarLogin(cliente);



                    }else{
                        Toast.makeText(TelaLoginCLI.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT).show();
                    }



                }else{
                    Toast.makeText(TelaLoginCLI.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarLogin( Cliente cliente ){

        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();


        autenticacao.signInWithEmailAndPassword(
                cliente.getEmail(),
                cliente.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    startActivity(new Intent(TelaLoginCLI.this, TelaPrincipal.class));
                    finish();

                }else{

                    Toast.makeText(TelaLoginCLI.this,
                            "Email ou senha incorreta(o) " ,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void recuperarSenha(View view){
        Intent i = new Intent(TelaLoginCLI.this, TelaRecuperarSenha.class);
        startActivity( i );
    }

    public void abrirCadastroCLI(View view){
        Intent i = new Intent(TelaLoginCLI.this, TelaCadastroCLI.class);
        startActivity( i );
    }

    public void inicializarComponentes() {
        edtEmail = findViewById(R.id.emailLoginCLI);
        edtSenha = findViewById(R.id.senhaLoginCLI);
        bttnLogin = findViewById(R.id.bttnLoginCLI);
    }
}
