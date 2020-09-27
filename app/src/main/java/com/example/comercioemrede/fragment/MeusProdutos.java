package com.example.comercioemrede.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.comercioemrede.R;
import com.example.comercioemrede.activity.RedirectCadastro;
import com.example.comercioemrede.activity.TelaCadastroPRO;
import com.example.comercioemrede.activity.TelaPrincipal;
import com.example.comercioemrede.adapter.CatalogoAdapter;
import com.example.comercioemrede.controller.Catalogo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MeusProdutos extends Fragment {

    private RecyclerView mRecyclerViewCatalogos;
    private CatalogoAdapter adapter;
    private List<Catalogo> catalogos;
    private DatabaseReference referenciaFirebase;
    private Catalogo todosCatalogos;
    private LinearLayoutManager mLayoutManagerTodosProdutos;

    public MeusProdutos() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_produtos, container,false);

        mRecyclerViewCatalogos = (RecyclerView) view.findViewById(R.id.recyclerViewTodosProdutos);
        carregarTodosProdutos();

        FloatingActionButton Adicionar = (FloatingActionButton) view.findViewById(R.id.fabAdicionar);
        Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TelaCadastroPRO.class);
                it.putExtra("some","some data");
                startActivity(it);
            }
        });
        return view;
        }
    private void carregarTodosProdutos() {

        mRecyclerViewCatalogos.setHasFixedSize(true);

        mLayoutManagerTodosProdutos = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewCatalogos.setLayoutManager(mLayoutManagerTodosProdutos);

        catalogos = new ArrayList<>();

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        referenciaFirebase.child("catalogo").orderByChild("nome").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    todosCatalogos = postSnapshot.getValue(Catalogo.class);

                    catalogos.add(todosCatalogos);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapter = new CatalogoAdapter(catalogos, getContext());

        mRecyclerViewCatalogos.setAdapter(adapter);
    }

}

