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
import com.example.comercioemrede.controller.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastroCLI extends AppCompatActivity {



    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private Button bttnCadastro;

    private Cliente cliente;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_cli);

        inicializarComponentes();

        bttnCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String nomeCLI = edtNome.getText().toString();
                String emailCLI = edtEmail.getText().toString();
                String senhaCLI = edtSenha.getText().toString();
                String confirmarSenhaCLI = edtConfirmarSenha.getText().toString();


                if ( !nomeCLI.isEmpty() ){
                    if ( !emailCLI.isEmpty() ){
                            if ( !senhaCLI.isEmpty() ){
                                if ( !confirmarSenhaCLI.isEmpty() ){
                                        if ( confirmarSenhaCLI.equals(senhaCLI) ) {

                                            cliente = new Cliente();
                                            cliente.setNome(nomeCLI);
                                            cliente.setEmail(emailCLI);
                                            cliente.setSenha(senhaCLI);
                                            cadastrar(cliente);

                                        }else {
                                            Toast.makeText(TelaCadastroCLI.this,
                                                    "as senhas não se coincidem!",
                                                    Toast.LENGTH_SHORT).show();

                                        }


                                    }else{
                                    Toast.makeText(TelaCadastroCLI.this,
                                            "Preencha a senha!",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(TelaCadastroCLI.this,
                                        "Preencha a senha!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                        Toast.makeText(TelaCadastroCLI.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TelaCadastroCLI.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void abrirLoginCLI(View view) {
        Intent i = new Intent(TelaCadastroCLI.this, TelaLoginCLI.class);
        startActivity(i);
    }


    public void cadastrar(final Cliente cliente){

        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                cliente.getEmail(),
                cliente.getSenha()

        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if ( task.isSuccessful() ) {
                            try {
                                //Salvar dados
                                String cod_usu = task.getResult().getUser().getUid();
                                cliente.setCod_usu(cod_usu);
                                cliente.salvarCliente();

                            Toast.makeText(TelaCadastroCLI.this,
                                    "Cadastrado com sucesso ",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), TelaLoginCLI.class));
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

                            Toast.makeText(TelaCadastroCLI.this,
                                    "Erro " +erroExcecao ,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );

    }
    public void inicializarComponentes(){
            edtNome = findViewById(R.id.nomeCLI);
            edtEmail = findViewById(R.id.emailLoginCLI);
            edtSenha = findViewById(R.id.senhaLoginCLI);
            edtConfirmarSenha = findViewById(R.id.confirmarSenhaCLI);
            bttnCadastro = findViewById(R.id.bttnCadastroCLI);

    }
    public void voltar(View view) {
        finish(); //finalza a activity porque não tem necessidade de inicializar a fragment novamente :P
    }
}
