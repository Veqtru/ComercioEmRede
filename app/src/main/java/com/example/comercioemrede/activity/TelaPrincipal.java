package com.example.comercioemrede.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.comercioemrede.R;
import com.example.comercioemrede.controller.Lojista;
import com.example.comercioemrede.controller.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaPrincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_inicio, R.id.nav_buscar)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Maps(View view){
        Intent it = new Intent(TelaPrincipal.this, MapsActivity.class);
        startActivity(it);
    }
    public void redirectCadastro(View view){
        Intent it = new Intent(TelaPrincipal.this, RedirectCadastro.class);
        startActivity(it);
    }
    public void redirectLogin(View view){
        Intent it = new Intent(TelaPrincipal.this, RedirectLogin.class);
        startActivity(it);
    }
}
