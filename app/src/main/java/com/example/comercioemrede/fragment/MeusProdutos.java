package com.example.comercioemrede.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.comercioemrede.R;
import com.example.comercioemrede.activity.RedirectLogin;
import com.example.comercioemrede.activity.TelaCadastroPRO;
import com.example.comercioemrede.activity.TelaEditarPRO;
import com.example.comercioemrede.activity.TelaLoginCLI;
import com.example.comercioemrede.activity.TelaLoginLOJ;
import com.example.comercioemrede.adapter.CatalogoAdapter;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.example.comercioemrede.helper.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class MeusProdutos extends Fragment {

    private RecyclerView recyclerProdutos;
    private List<Catalogo> catalogos = new ArrayList<>();
    private CatalogoAdapter catalogoAdapter;
    private DatabaseReference produtoUsuarioRef;
    private Catalogo catalogo;
    private Button excluir;
    private Button editar;
    private AlertDialog dialog;

    public MeusProdutos(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).findViewById(R.id.ConstraintLayoutBar).setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).findViewById(R.id.searchViewBar).setVisibility(View.GONE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meus_produtos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        produtoUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("catalogo")
                .child( ConfiguracaoFirebase.getIdUsuario() );

        //inicializarComponentes();
        recyclerProdutos = (RecyclerView) getView().findViewById(R.id.recyclerProdutos);
        FloatingActionButton Adicionar = (FloatingActionButton) getView().findViewById(R.id.fabAdicionar);
        Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TelaCadastroPRO.class);
                //it.putExtra("some","some data");
                startActivity(it);
            }
        });

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerProdutos.setHasFixedSize(true);
        catalogoAdapter = new CatalogoAdapter(catalogos, getContext());
        recyclerProdutos.setAdapter( catalogoAdapter );

        recuperarProdutos();

        recyclerProdutos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerProdutos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Catalogo selecionado = catalogos.get(position);

                                Intent it = new Intent(getContext(), TelaEditarPRO.class);
                                it.putExtra("prod",selecionado);
                                startActivity(it);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                /*Catalogo anuncioSelecionado = catalogos.get(position);
                                anuncioSelecionado.remover();*/

                            }
                        }
                )
        );

    }
    private void recuperarProdutos(){

        dialog = new SpotsDialog.Builder()
                .setContext( getContext() )
                .setMessage("Recuperando Produtos")
                .setCancelable( false )
                .build();
        dialog.show();

        produtoUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                catalogos.clear();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    catalogos.add( ds.getValue(Catalogo.class) );
                }

                Collections.reverse( catalogos );
                catalogoAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    /*public void inicializarComponentes(){
        recyclerProdutos = (RecyclerView) getActivity().findViewById(R.id.recyclerProdutos);
    }*/

}

