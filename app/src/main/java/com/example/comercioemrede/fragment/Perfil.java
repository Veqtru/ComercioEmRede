package com.example.comercioemrede.fragment;

import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.comercioemrede.R;

public class Perfil extends Fragment {

    public Perfil(){
    }
    
    public View OnCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


}
