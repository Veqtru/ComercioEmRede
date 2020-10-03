package com.example.comercioemrede.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.example.comercioemrede.helper.Permissoes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;


public class TelaCadastroPRO extends AppCompatActivity implements View.OnClickListener {

    private Spinner spTipoPRO;
    private EditText edtNomePRO, edtDescricaoPRO;
    private CurrencyEditText edtPrecoPRO;
    private ImageView imgCadastro1, imgCadastro2, imgCadastro3;
    private AlertDialog dialog;

    private Catalogo catalogo;

    private StorageReference storage;

    private String[] permissoes = new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_pro);

        //Configurações iniciais
        storage = ConfiguracaoFirebase.getFirebaseStorage();

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
        carregarDadosSpinner();

    }
    public void cadastrarProduto(){

        dialog = new SpotsDialog.Builder()
                .setContext( this )
                .setMessage("Salvando Produto")
                .setCancelable( false )
                .build();
        dialog.show();

        for (int i=0; i < listaFotosRecuperadas.size(); i++){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);
        }
    }
    private void salvarFotoStorage(String urlString, final int totalFotos, int contador){

        final StorageReference imagemProduto = storage.child("imagens")
                .child("produtos")
                .child( catalogo.getKeyProduto() )
                .child("imagem"+ contador);

        //FAZER UPLOAD DAS FOTOS
        UploadTask uploadTask = imagemProduto.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagemProduto.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri url = task.getResult();
                        String urlConvertida = url.toString();

                        listaURLFotos.add( urlConvertida );

                        if ( totalFotos == listaURLFotos.size() ){
                            catalogo.setFotos( listaURLFotos );
                            catalogo.salvar();

                            dialog.dismiss();
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer o uplaod");
                Log.i("INFO", "Falha ao fazer o upload: " + e.getMessage());
            }
        });

    }
    public Catalogo configurarProduto(){

        String tipo = spTipoPRO.getSelectedItem().toString();
        String nome = edtNomePRO.getText().toString();
        String preco = edtPrecoPRO.getText().toString();
        Catalogo catalogo = new Catalogo();
        catalogo.setNome( nome );
        catalogo.setTipo( tipo );
        catalogo.setPreco( preco );

        return catalogo;
    }
    public void validarDados(View view){

        String preco = String.valueOf(edtPrecoPRO.getRawValue());

        catalogo = configurarProduto();

        if ( listaFotosRecuperadas.size() != 0 ){
            if ( !catalogo.getTipo().isEmpty() ){
                if ( !catalogo.getNome().isEmpty() ){
                    if ( !preco.isEmpty() && !preco.equals("0") ){
                        cadastrarProduto();
                    }else {
                        exibirMensagemErro("Preencha o campo preço!");
                    }

                }else {
                    exibirMensagemErro("Preencha o campo nome!");
                }

            }else {
                exibirMensagemErro("Preencha o campo tipo!");
            }
        }else {
            exibirMensagemErro("Seleione uma foto!");
        }

    }
    private  void exibirMensagemErro(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ){
            case R.id.imgCadastro1 :
                escolherimagem(1);
                break;
            case R.id.imgCadastro2 :
                escolherimagem(2);
                break;
            case R.id.imgCadastro3 :
                escolherimagem(3);
                break;
        }
    }
    public void escolherimagem(int requestCode){
        Intent i  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK){

            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            if ( requestCode == 1 ){
                imgCadastro1.setImageURI( imagemSelecionada );
            }else if ( requestCode == 2 ){
                imgCadastro2.setImageURI( imagemSelecionada );
            }else if ( requestCode == 3 ) {
                imgCadastro3.setImageURI(imagemSelecionada);
            }
            listaFotosRecuperadas.add( caminhoImagem );
        }
    }
    public void carregarDadosSpinner(){

        String[] tipo = new String[]{
                "Bebidas", "Carnes", "Guloseimas", "Higiene", "Hortifruti", "Limpeza", "Não perecíveis", "Padaria"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                tipo

        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spTipoPRO.setAdapter( adapter );
    }
    public void inicializarComponentes() {

        edtNomePRO = findViewById(R.id.edtNomePRO);
        edtDescricaoPRO = findViewById(R.id.edtDescricaoPRO);
        edtPrecoPRO = findViewById(R.id.edtPrecoPRO);
        spTipoPRO = findViewById(R.id.spTipoPRO);
        imgCadastro1 = findViewById(R.id.imgCadastro1);
        imgCadastro2 = findViewById(R.id.imgCadastro2);
        imgCadastro3 = findViewById(R.id.imgCadastro3);
        imgCadastro1.setOnClickListener(this);
        imgCadastro2.setOnClickListener(this);
        imgCadastro3.setOnClickListener(this);

        Locale locale = new Locale("pt", "BR");
        edtPrecoPRO.setLocale( locale );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for ( int permissaoResultado : grantResults){
            if ( permissaoResultado== PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }
    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o aplicativo é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

