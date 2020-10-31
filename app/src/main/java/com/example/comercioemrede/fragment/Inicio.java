package com.example.comercioemrede.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.comercioemrede.R;
import com.example.comercioemrede.activity.TelaCadastroPRO;
import com.example.comercioemrede.activity.TelaEditarPRO;
import com.example.comercioemrede.adapter.CatalogoAdapter;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.example.comercioemrede.helper.RecyclerItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Inicio extends Fragment {

    private RecyclerView recyclerProdutos;
    private List<Catalogo> listacatalogos = new ArrayList<>();
    private CatalogoAdapter catalogoAdapter;
    private DatabaseReference produtoUsuarioRef;
    private Catalogo catalogo;
    private AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).findViewById(R.id.ConstraintLayoutBar).setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).findViewById(R.id.searchViewBar).setVisibility(View.VISIBLE);

        produtoUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("catalogo_publico");

        recyclerProdutos = (RecyclerView) getView().findViewById(R.id.recyclerProdutosPublicos);
        recyclerProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerProdutos.setHasFixedSize(true);
        catalogoAdapter = new CatalogoAdapter(listacatalogos, getContext());
        recyclerProdutos.setAdapter( catalogoAdapter );

        recuperarProdutos();

        recyclerProdutos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerProdutos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Catalogo selecionado = listacatalogos.get(position);

                                /*Intent it = new Intent(getContext(), TelaEditarPRO.class);
                                it.putExtra("prod",selecionado);
                                startActivity(it);*/
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

                listacatalogos.clear();
                for ( DataSnapshot tipo : dataSnapshot.getChildren() ){
                    for ( DataSnapshot keyProduto : tipo.getChildren() ) {

                        Catalogo catalogo = keyProduto.getValue(Catalogo.class);
                        listacatalogos.add(catalogo);
                    }
                }

                Collections.reverse( listacatalogos );
                catalogoAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
