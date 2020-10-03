package com.example.comercioemrede.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comercioemrede.R;
import com.example.comercioemrede.activity.TelaCadastroPRO;
import com.example.comercioemrede.adapter.CatalogoAdapter;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MeusProdutos extends Fragment {

    private RecyclerView recyclerProdutos;
    private List<Catalogo> catalogos = new ArrayList<>();
    private CatalogoAdapter catalogoAdapter;
    private DatabaseReference produtoUsuarioRef;

    public MeusProdutos(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                it.putExtra("some","some data");
                startActivity(it);
            }
        });

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerProdutos.setHasFixedSize(true);
        catalogoAdapter = new CatalogoAdapter(catalogos, getContext());
        recyclerProdutos.setAdapter( catalogoAdapter );

        recuperarProdutos();


    }
    private void recuperarProdutos(){

        produtoUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                catalogos.clear();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    catalogos.add( ds.getValue(Catalogo.class) );
                }

                Collections.reverse( catalogos );
                catalogoAdapter.notifyDataSetChanged();

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

