package com.example.jelouscrab.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jelouscrab.R;

import java.util.ArrayList;

public class FavoritoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Producto> listaFav;
    LayoutInflater inflater;

    public FavoritoAdapter(Context context, ArrayList<Producto> listaFav) {
        this.context = context;
        this.listaFav = listaFav;
    }

    @Override
    public int getCount() {
        return listaFav.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.gImage);
        TextView name = convertView.findViewById(R.id.gName);
        TextView id = convertView.findViewById(R.id.gId);
        TextView description = convertView.findViewById(R.id.gDesc);
        TextView price = convertView.findViewById(R.id.gPrice);
        ImageView star = convertView.findViewById(R.id.star);

        Producto producto = listaFav.get(position);
        byte[] image = producto.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        imageView.setImageBitmap(bitmap);
        name.setText(producto.getName());
        id.setText("Código: "+producto.getId());
        description.setText(producto.getDescription());
        price.setText(producto.getPrice());
        star.setImageResource(R.drawable.estrella);

        return convertView;
    }
}
