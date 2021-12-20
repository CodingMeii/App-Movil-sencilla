package com.example.jelouscrab.ui.sucursales;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jelouscrab.R;
import com.example.jelouscrab.databinding.FragmentSucursalesBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class SucursalesFragment extends Fragment {

    private MapView myOpenMapView;
    private MapController mapController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sucursales, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Configuration.getInstance().load(
                getContext(), PreferenceManager.getDefaultSharedPreferences(getContext())
        );

        GeoPoint kennedy = new GeoPoint(4.6164, -74.1535);

        myOpenMapView = view.findViewById(R.id.mapa);
        myOpenMapView.setBuiltInZoomControls(true);

        mapController = (MapController) myOpenMapView.getController();
        mapController.setCenter(kennedy);
        mapController.setZoom(18.0);

        myOpenMapView.setMultiTouchControls(true);

        //Sucursales

        ArrayList<OverlayItem> sucursal = new ArrayList<OverlayItem>();
        sucursal.add(new OverlayItem("Kennedy", "Hospital de kennedy", kennedy));
        sucursal.add(new OverlayItem("Kennedy", "Parque timiza", new GeoPoint(4.6089, -74.914)));
        sucursal.add(new OverlayItem("Chapinero", "Centro comercial andino", new GeoPoint(4.5981, -74.1513)));
        sucursal.add(new OverlayItem("Teusaquillo", "Universidad nacional", new GeoPoint(4.6387, -74.0852)));
        sucursal.add(new OverlayItem("Barrios Unidos", "Universidad Sergio Arboleda", new GeoPoint(4.6611, -74.0596)));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };
        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(getContext(), sucursal, tap);
        capa.setFocusItemsOnTap(true);
        myOpenMapView.getOverlays().add(capa);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}