package com.example.comercioemrede.adapter;

import android.content.Context;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Catalogo;
import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.MyViewHolder>{

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
        holder.preco.setText("R$ "+catalogo.getPreco() );


        if (catalogo.getOferta() == null){
            holder.ofertaPreco.setVisibility(View.GONE);
            holder.ofertaPorcentagem.setVisibility(View.GONE);
            holder.ofertaValidade.setVisibility(View.GONE);
        }else {
            Double precoA = Double. parseDouble(catalogo.getPreco());
            Double ofertaA = Double. parseDouble("0."+catalogo.getOferta());
            Double desconto =  precoA * ofertaA;
            Double precoOferta = precoA - desconto;
            holder.ofertaPreco.setText("R$ "+Double.toString(precoOferta) );
            holder.ofertaPorcentagem.setText(catalogo.getOferta()+"%" );
            holder.ofertaValidade.setText( catalogo.getValidadeOferta() );
            holder.preco.setPaintFlags(holder.preco.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.ofertaPreco.setVisibility(View.VISIBLE);
            holder.ofertaPorcentagem.setVisibility(View.VISIBLE);
            holder.ofertaValidade.setVisibility(View.VISIBLE);
        }

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
        TextView ofertaPorcentagem;
        TextView ofertaPreco;
        TextView ofertaValidade;
        CardView cardViewPRO;



        public MyViewHolder(View itemView){
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomePRO);
            preco = itemView.findViewById(R.id.txtPrecoPRO);
            foto = itemView.findViewById(R.id.imgProduto);
            cardViewPRO = itemView.findViewById(R.id.cardViewPRO);
            ofertaPorcentagem = itemView.findViewById(R.id.txtOfertaPorcentagem);
            ofertaPreco = itemView.findViewById(R.id.txtOfertaPreco);
            ofertaValidade = itemView.findViewById(R.id.txtOfertaValidade);

        }

    }
    public static String formatValue(double value) {
        int power;
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("###,###,###.##");


        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }

}