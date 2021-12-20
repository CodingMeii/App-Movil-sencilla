package com.example.jelouscrab.ui.servicios;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.jelouscrab.R;
import com.example.jelouscrab.datos.ApiOracle;
import com.example.jelouscrab.datos.DBHelper;
import com.example.jelouscrab.modelo.Servicio;
import com.example.jelouscrab.modelo.ServicioAdapter;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {
    /*------ SQLite ------*/
    //private DBHelper dbHelper;
    //private ArrayList<Producto> listaProductos;

    private ProgressBar progressBar;
    private GridView gridView;

    /*------ Api Oracle ------*/
    private ApiOracle apiOracle;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_servicios, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*------ SQLite ------*/
        //dbHelper = new DBHelper(getContext());

        gridView = view.findViewById(R.id.gridView);

        /*------ Api Oracle ------*/
        apiOracle = new ApiOracle(getContext());

        progressBar = view.findViewById(R.id.carga2);
        apiOracle.getAllServices(gridView, progressBar);

        /*Cursor cursor = dbHelper.getServicios();
        listaServicios = llenarLista(cursor);

        ServicioAdapter servicioAdapter = new ServicioAdapter(getContext(), listaServicios);
        gridView.setAdapter(servicioAdapter);*/
    }

    public ArrayList<Servicio> llenarLista(Cursor cursor){
        ArrayList<Servicio> list = new ArrayList<>();

        if(cursor.getCount() == 0){
            return list;
        }else{
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()){
                Servicio servicio = new Servicio(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3)
                );

                list.add(servicio);
            }

            return list;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}