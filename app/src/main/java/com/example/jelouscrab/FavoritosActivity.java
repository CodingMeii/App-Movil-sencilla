package com.example.jelouscrab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.jelouscrab.datos.ApiOracle;

public class FavoritosActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private GridView gridView;

    /*------ Api Oracle ------*/
    private ApiOracle apiOracle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        gridView = findViewById(R.id.gridView);

        /*------ Api Oracle ------*/
        apiOracle = new ApiOracle(getApplicationContext());

        progressBar = findViewById(R.id.carga3);
        apiOracle.getProductosFavoritos(gridView, progressBar);
    }
}