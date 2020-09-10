package com.example.comercioemrede.adapter;
//cardapio = catalago

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercioemrede.R;
import com.example.comercioemrede.model.Catalogo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ViewHolder> {

    private List<Catalogo> mCatalogoList;
    private Context context;
    private DatabaseReference referenciaFirebase;
    private List<Catalogo> catalogos;
    private Catalogo todos_produtos;

    public CatalogoAdapter(List<Catalogo> l, Context c) {
        context = c;
        mCatalogoList = l;
    }

    @Override
    public CatalogoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_todos_produtos, viewGroup, false);

        return new CatalogoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CatalogoAdapter.ViewHolder holder, int position) {

        final Catalogo item = mCatalogoList.get(position);
        catalogos = new ArrayList<>();

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        referenciaFirebase.child("catalogo").orderByChild("keyProduto").equalTo(item.getKeyProduto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                catalogos.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    todos_produtos = postSnapshot.getValue(Catalogo.class);

                    catalogos.add(todos_produtos);

                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

                    final int height = (displayMetrics.heightPixels / 4);
                    final int width = (displayMetrics.widthPixels / 2);

                    Picasso.with(context).load(todos_produtos.getUrlImage()).resize(width, height).centerCrop().into(holder.imgFotoPRO);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.edtNomePRO.setText(item.getNome());
        holder.edtPrecoPRO.setText(item.getNome());
        holder.edtQuantidadePRO.setText(item.getNome());
        holder.edtTipoPRO.setText(item.getNome());

        holder.linearLayoutPRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCatalogoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView edtNomePRO;
        protected TextView edtTipoPRO;
        protected TextView edtQuantidadePRO;
        protected TextView edtPrecoPRO;
        protected ImageView imgFotoPRO;
        protected LinearLayout linearLayoutPRO;

        public ViewHolder (View itemView){
            super(itemView);

            edtNomePRO = (TextView)itemView.findViewById(R.id.edtNomePRO);
            edtTipoPRO = (TextView)itemView.findViewById(R.id.edtTipoPRO);
            edtQuantidadePRO = (TextView)itemView.findViewById(R.id.edtQuantidadePRO);
            edtPrecoPRO = (TextView)itemView.findViewById(R.id.edtPrecoPRO);
            imgFotoPRO = (ImageView)itemView.findViewById(R.id.imgFotoPRO);
            linearLayoutPRO = (LinearLayout)itemView.findViewById(R.id.linearLayoutCatalogoprodutos);
        }
    }
}