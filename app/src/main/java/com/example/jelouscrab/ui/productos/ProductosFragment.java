package com.example.jelouscrab.ui.productos;

import androidx.annotation.Nullable;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.jelouscrab.R;
import com.example.jelouscrab.datos.ApiOracle;
import com.example.jelouscrab.datos.DBHelper;
import com.example.jelouscrab.modelo.Producto;
import com.example.jelouscrab.modelo.ProductoAdapter;

import java.util.ArrayList;

public class ProductosFragment extends Fragment {
    /*------ SQLite ------*/
    //private DBHelper dbHelper;
    //private ArrayList<Producto> listaProductos;

    private ProgressBar progressBar;
    private GridView gridView;

    /*------ Api Oracle ------*/
    private ApiOracle apiOracle;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);

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

        progressBar = view.findViewById(R.id.carga);
        apiOracle.getAllProductos(gridView, progressBar);

        /*Cursor cursor = dbHelper.getProductos();
        listaProductos = llenarLista(cursor);

        ProductoAdapter productosAdapter = new ProductoAdapter(getContext(), listaProductos);
        gridView.setAdapter(productosAdapter);*/
    }

    public ArrayList<Producto> llenarLista(Cursor cursor){
        ArrayList<Producto> list = new ArrayList<>();

        if(cursor.getCount() == 0){
            return list;
        }else{
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()){
                Producto producto = new Producto(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );

                list.add(producto);
            }

            return list;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}