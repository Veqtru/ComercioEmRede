package com.example.comercioemrede.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.comercioemrede.adapter.CatalogoAdapter;
import com.example.comercioemrede.R;
import com.example.comercioemrede.model.Catalogo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewCatalogos;
    private CatalogoAdapter adapter;
    private List<Catalogo> produtos;
    private DatabaseReference referenciaFirebase;
    private Catalogo todosProdutos;
    private LinearLayoutManager mLayoutManagerTodosProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        mRecyclerViewCatalogos = (RecyclerView) findViewById(R.id.recylerViewTodosProdutos);

        carregarTodosProdutos();
    }

    private void carregarTodosProdutos() {

        mRecyclerViewCatalogos.setHasFixedSize(true);
        mLayoutManagerTodosProdutos = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCatalogos.setLayoutManager(mLayoutManagerTodosProdutos);
        produtos = new ArrayList<>();
        referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        referenciaFirebase.child("catalogo").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    todosProdutos = postSnapshot.getValue(Catalogo.class);

                    produtos.add(todosProdutos);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapter = new CatalogoAdapter(produtos, this);

        mRecyclerViewCatalogos.setAdapter(adapter);
    }
}