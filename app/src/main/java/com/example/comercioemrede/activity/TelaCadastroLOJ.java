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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastroLOJ extends AppCompatActivity {


    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtTelefone;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private EditText edtEndereco;
    private EditText edtCnpj;
    private Button bttnCadastro;

    private Lojista lojista;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_loj);

        inicializarComponentes();

        bttnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeLOJ = edtNome.getText().toString();
                String emailLOJ = edtEmail.getText().toString();
                String telefoneLOJ = edtTelefone.getText().toString();
                String senhaLOJ = edtSenha.getText().toString();
                String confirmarSenhaLOJ = edtConfirmarSenha.getText().toString();
                String cnpjLOJ = edtCnpj.getText().toString();
                String enderecoLOJ = edtEndereco.getText().toString();

                if (!nomeLOJ.isEmpty()) {
                    if (!emailLOJ.isEmpty()) {
                        if (!telefoneLOJ.isEmpty()) {
                            if (!senhaLOJ.isEmpty()) {
                                if (!confirmarSenhaLOJ.isEmpty()) {
                                    if (!cnpjLOJ.isEmpty()) {
                                        if (!enderecoLOJ.isEmpty()) {
                                            if (confirmarSenhaLOJ.equals(senhaLOJ)) {

                                                lojista = new Lojista();
                                                lojista.setNome(nomeLOJ);
                                                lojista.setEmail(emailLOJ);
                                                lojista.setSenha(senhaLOJ);
                                                lojista.setTelefone(telefoneLOJ);
                                                lojista.setCnpj(cnpjLOJ);
                                                lojista.setEndereco(enderecoLOJ);
                                                cadastrar(lojista);

                                            } else {
                                                Toast.makeText(TelaCadastroLOJ.this,
                                                        "as senhas não se coincidem!",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(TelaCadastroLOJ.this,
                                                    "Preencha o CEP",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(TelaCadastroLOJ.this,
                                                "Preencha o CNPJ!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(TelaCadastroLOJ.this,
                                            "Preencha a senha!",
                                            Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(TelaCadastroLOJ.this,
                                        "Preencha a senha!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(TelaCadastroLOJ.this,
                                    "Preencha o telefone!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TelaCadastroLOJ.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(TelaCadastroLOJ.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrar(final Lojista lojista ){

        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                lojista.getEmail(),
                lojista.getSenha()

        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if ( task.isSuccessful() ) {
                            try {
                                //Salvar dados
                                String cod_usu = task.getResult().getUser().getUid();
                                lojista.setCod_usu(cod_usu);
                                lojista.salvarLojista();

                                Toast.makeText(TelaCadastroLOJ.this,
                                        "Cadastrado com sucesso ",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), TelaLoginLOJ.class));
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }else{
                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Por favor, digite um email válido!";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Esta conta já foi cadastrada!";
                            }catch (Exception e){
                                erroExcecao = "ao cadastrar cliente: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(TelaCadastroLOJ.this,
                                    "Erro " +erroExcecao ,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );

        }

    public void abrirLoginLOJ(View view) {
        Intent i = new Intent(TelaCadastroLOJ.this, TelaLoginLOJ.class);
        startActivity(i);
    }

    public void inicializarComponentes() {
            edtNome = findViewById(R.id.nomeLOJ);
            edtEmail = findViewById(R.id.emailLoginLOJ);
            edtTelefone = findViewById(R.id.telefoneLOJ);
            edtSenha = findViewById(R.id.senhaLoginLOJ);
            edtConfirmarSenha = findViewById(R.id.confirmarSenhaLOJ);
            edtCnpj = findViewById(R.id.cnpjLOJ);
            edtEndereco = findViewById(R.id.enderecoLOJ);
            bttnCadastro = findViewById(R.id.bttnCadastroLOJ);
    }
    public void voltar(View view) {
        finish(); //finalza a activity porque não tem necessidade de inicializar a fragment novamente :P
    }
}