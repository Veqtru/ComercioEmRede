package com.example.comercioemrede.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.comercioemrede.R;
import com.example.comercioemrede.activity.TelaPrincipal;
import com.example.comercioemrede.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class Sair extends Fragment {

    private FirebaseAuth autenticacao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exibirConfirmação();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avaliados, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void exibirConfirmação(){
        final AlertDialog.Builder magBox = new AlertDialog.Builder(getContext());
        magBox.setTitle("Saindo...");
        magBox.setMessage("Tem certeza que deseja sair ?");
        magBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
                autenticacao.signOut();

                Intent intent = new Intent(getActivity(), TelaPrincipal.class);
                startActivity(intent);
            }
        });
        magBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        magBox.show();
    }
}
