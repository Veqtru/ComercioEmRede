package com.example.comercioemrede.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Catalogo;
import com.example.comercioemrede.fragment.MeusProdutos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.MyViewHolder> {

    private List<Catalogo> catalogos;
    private Context context;

    public CatalogoAdapter(List<Catalogo> catalogos, Context context) {
        this.catalogos = catalogos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_catalogo, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Catalogo catalogo = catalogos.get(position);
        holder.nome.setText( catalogo.getNome() );
        holder.preco.setText( catalogo.getPreco() );

        List<String> urlFotos = catalogo.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.with(context).load(urlCapa).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return catalogos.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView preco;
        ImageView foto;
        CardView cardViewPRO;

        public MyViewHolder(View itemView){
            super(itemView);

            nome = itemView.findViewById(R.id.edtNomePRO);
            preco = itemView.findViewById(R.id.edtPrecoPRO);
            foto = itemView.findViewById(R.id.imgProduto);
            cardViewPRO = itemView.findViewById(R.id.cardViewPRO);

        }

    }

}