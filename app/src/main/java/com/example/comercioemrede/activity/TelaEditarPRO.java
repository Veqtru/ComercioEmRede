package com.example.comercioemrede.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.bumptech.glide.Glide;
import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.example.comercioemrede.helper.Permissoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TelaEditarPRO extends AppCompatActivity{


    private Catalogo catalogo;
    //catalogo ou produtoSelecionado
    private EditText editNome, editDescricao;
    private CurrencyEditText editPreco;
    private Spinner editTipo;
    private ImageView imagem1, imagem2, imagem3;
    private CheckBox chOferta;


    List<String> tipo = new ArrayList<String>();

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

        //Configurações iniciais
        storage = ConfiguracaoFirebase.getFirebaseStorage();

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);


        this.catalogo = (Catalogo) getIntent().getSerializableExtra("prod");
        inicializarComponentes();
        carregarDadosSpinner();
        editNome.setText(this.catalogo.getNome());
        editPreco.setText(this.catalogo.getPreco());
        editTipo.setSelection(this.tipo.indexOf(this.catalogo.getTipo()));
        editDescricao.setText(this.catalogo.getDescricao());


        switch (this.catalogo.getFotos().size()){
            case 3:
                Glide.with(TelaEditarPRO.this).load(Uri.parse(this.catalogo.getFotos().get(2))).into(imagem3);
            case 2:
                Glide.with(TelaEditarPRO.this).load(Uri.parse(this.catalogo.getFotos().get(1))).into(imagem2);
            case 1:
                Glide.with(TelaEditarPRO.this).load(Uri.parse(this.catalogo.getFotos().get(0))).into(imagem1);
                break;
        }








        FloatingActionButton excluir = findViewById(R.id.fabExcluir);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //excluir dados realtimedatabase
                catalogo.remover();
                //excluir fotos do storage

              /*  final StorageReference imagemProduto = storage.child("imagens")
                        .child("produtos")
                        .child( catalogo.getKeyProduto() )
                        .child("imagem"+ contador);
*/



                finish();
            }
        });
    }
    public void carregarDadosSpinner(){
        this.tipo.clear();
        this.tipo.add("Bebidas");
        this.tipo.add( "Carnes");
        this.tipo.add( "Guloseimas");
        this.tipo.add( "Higiene");
        this.tipo.add( "Hortifruti");
        this.tipo.add("Limpeza");
        this.tipo.add( "Não perecíveis");
        this.tipo.add( "Padaria");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                tipo

        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        editTipo.setAdapter( adapter );

    }
    public void inicializarComponentes(){
        editNome  = (EditText) findViewById(R.id.edtNomePRO);
        chOferta = (CheckBox) findViewById(R.id.chOfertaPRO);
        editDescricao  = (EditText) findViewById(R.id.edtDescricaoPRO);
        editTipo = (Spinner) findViewById(R.id.spTipoPRO);
        editPreco = (CurrencyEditText) findViewById(R.id.edtPrecoPRO);
        imagem1 = findViewById(R.id.imgCadastro1);
        imagem2 = findViewById(R.id.imgCadastro2);
        imagem3 = findViewById(R.id.imgCadastro3);




        Locale locale = new Locale("pt", "BR");
        editPreco.setLocale( locale );
    }
    public void exibirOferta(View view){

        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.chOfertaPRO:
                if (checked) {
                    findViewById(R.id.edtPrecoOfertaPRO).setVisibility(View.VISIBLE);
                    findViewById(R.id.edtValidadeOfertaPRO).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.edtPrecoOfertaPRO).setVisibility(View.GONE);
                    findViewById(R.id.edtValidadeOfertaPRO).setVisibility(View.GONE);
                }
                break;
        }
    }

}
