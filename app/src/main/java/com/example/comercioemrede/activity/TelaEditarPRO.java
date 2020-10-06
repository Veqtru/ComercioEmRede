package com.example.comercioemrede.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Catalogo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class TelaEditarPRO extends AppCompatActivity implements View.OnClickListener {


    private Catalogo catalogo;
    //catalogo ou produtoSelecionado
    private EditText editNome;
    private CurrencyEditText editPreco;
    private Spinner editTipo;
    private ImageView imagem1;
    private ImageView imagem2;
    private ImageView imagem3;

    private AlertDialog dialog;

    private StorageReference storage;
    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_pro);

        this.catalogo = (Catalogo) getIntent().getSerializableExtra("prod");
        inicializarComponentes();

        editNome.setText(this.catalogo.getNome());
        editPreco.setText(this.catalogo.getPreco());
        //editTipo.setSelection(this.ProdutoSelecionado.getTipo());
        //imagem1.setImageURI(this.catalogo.getFotos());
        //imagem2.setImageURI(this.catalogo.getFotos());
        //imagem3.setImageURI(this.catalogo.getFotos());


        carregarDadosSpinner();

        FloatingActionButton excluir = findViewById(R.id.fabExcluir);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //excluir dados realtimedatabase
                catalogo.remover();
                //excluir fotos do storage





                finish();
            }
        });
    }
    public void atualizarProduto(){

        dialog = new SpotsDialog.Builder()
                .setContext( this )
                .setMessage("Salvando Produto")
                .setCancelable( false )
                .build();
        dialog.show();

        for (int i=0; i < listaFotosRecuperadas.size(); i++){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            atualizarFotoStorage(urlImagem, tamanhoLista, i);
        }
    }
    private void atualizarFotoStorage(String urlString, final int totalFotos, int contador){

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
    private void excluirFotoStorage(String urlString, final int totalFotos, int contador){

        final StorageReference imagemProduto = storage.child("imagens")
                .child("produtos")
                .child( catalogo.getKeyProduto() )
                .child("imagem"+ contador);
                storage.delete();

    }
    public Catalogo configurarProduto(){

        String tipo = editTipo.getSelectedItem().toString();
        String nome = editNome.getText().toString();
        String preco = editPreco.getText().toString();
        Catalogo catalogo = new Catalogo();
        catalogo.setNome( nome );
        catalogo.setTipo( tipo );
        catalogo.setPreco( preco );

        return catalogo;
    }

    public void validarDados(View view){

        String preco = String.valueOf(editPreco.getRawValue());

        catalogo = configurarProduto();

        if ( listaFotosRecuperadas.size() != 0 ){
            if ( !catalogo.getTipo().isEmpty() ){
                if ( !catalogo.getNome().isEmpty() ){
                    if ( !preco.isEmpty() && !preco.equals("0") ){
                        atualizarProduto();
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
                imagem1.setImageURI( imagemSelecionada );
            }else if ( requestCode == 2 ){
                imagem2.setImageURI( imagemSelecionada );
            }else if ( requestCode == 3 ) {
                imagem3.setImageURI(imagemSelecionada);
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
        editTipo.setAdapter( adapter );
    }
    public void inicializarComponentes(){
        editNome  = (EditText) findViewById(R.id.edtNomePRO);
        editTipo = (Spinner) findViewById(R.id.spTipoPRO);
        editPreco = (CurrencyEditText) findViewById(R.id.edtPrecoPRO);
        imagem1 = findViewById(R.id.imgCadastro1);
        imagem2 = findViewById(R.id.imgCadastro2);
        imagem3 = findViewById(R.id.imgCadastro3);
        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);

        Locale locale = new Locale("pt", "BR");
        editPreco.setLocale( locale );
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
