package com.example.jelouscrab.datos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jelouscrab.casosUso.cuProducto;
import com.example.jelouscrab.casosUso.cuServicio;
import com.example.jelouscrab.modelo.FavoritoAdapter;
import com.example.jelouscrab.modelo.Producto;
import com.example.jelouscrab.modelo.ProductoAdapter;
import com.example.jelouscrab.modelo.Servicio;
import com.example.jelouscrab.modelo.ServicioAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class ApiOracle {
    private RequestQueue queue;
    private Context context;
    private cuProducto cuproducto;
    private cuServicio cuservicio;
    private String urlProducto = "https://g42655a46067c39-db202112191027.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/products/products";
    private  String urlServicio = "https://g42655a46067c39-db202112191027.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/services/services";

    public ApiOracle(Context context) {
        this.queue = Volley.newRequestQueue(context);
        cuproducto = new cuProducto();
        this.context = context;
    }

    /*--------------------------------- Funciones personalizadas para la tabla productos ---------------------------------*/

    /*
     * Método que inserta un nuevo producto en la tabla Productos
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertProducto(String name, String description, String price, ImageView imageView, int fav) {
        JSONObject json = new JSONObject();
        String image = cuproducto.imageViewToString(imageView);

        try {
            json.put("name", name);
            json.put("description", description);
            json.put("price", price);
            json.put("image", image);
            json.put("fav", fav);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlProducto, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    /*
     * Método que consulta todos los productos almacenados en la tabla productos
     * */
    public void getAllProductos(GridView gridView, ProgressBar progressBar) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlProducto, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    ArrayList<Producto> list = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        byte[] image = cuproducto.stringToByte(jsonObject.getString("image"));

                        Producto producto = new Producto(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                image
                        );

                        list.add(producto);
                    }

                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    ProductoAdapter productoAdapter = new ProductoAdapter(context, list);
                    gridView.setAdapter(productoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    /*
     * Método que consulta los productos relacionados con un ID
     * */
    public void getProductoById(String id, ImageView imageView, EditText name, EditText desc, EditText price, CheckBox checkBox){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlProducto+"/"+id, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    byte[] image = cuproducto.stringToByte(jsonObject.getString("image"));

                    Producto producto = new Producto(
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("price"),
                            image,
                            jsonObject.getString("fav")
                    );

                    byte[] imageP = producto.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageP, 0, image.length);
                    imageView.setImageBitmap(bitmap);

                    name.setText(producto.getName());
                    desc.setText(producto.getDescription());
                    price.setText(producto.getPrice());

                    String fav = producto.getFav();
                    int favorito = Integer.parseInt(fav);

                    if(favorito == 1){
                        checkBox.setChecked(true);
                    }else if(favorito == 0){
                        checkBox.setChecked(false);
                    }else{
                        checkBox.setChecked(false);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /*
     * Método que consulta los productos favoritos
     * */
    public void getProductosFavoritos(GridView gridView, ProgressBar progressBar){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlProducto+"/favorite/1", null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    ArrayList<Producto> list = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        byte[] image = cuproducto.stringToByte(jsonObject.getString("image"));

                        Producto producto = new Producto(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                image
                        );

                        list.add(producto);
                    }

                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    FavoritoAdapter favoritoAdapter = new FavoritoAdapter(context, list);
                    gridView.setAdapter(favoritoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    /*
     * Método que elimina un profucto dependiendo del ID digitado
     * */
    public void deleteProducto(String id){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlProducto+"/"+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    /*
     * Método que actualiza el producto indicado con el ID y la información diligenciada
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateProducto(String id, String name, String description, String price, ImageView imageView, String fav){
        JSONObject json = new JSONObject();
        String image = cuproducto.imageViewToString(imageView);

        int favorito = Integer.parseInt(fav);

        try {
            json.put("id", id);
            json.put("name", name);
            json.put("description", description);
            json.put("price", price);
            json.put("image", image);
            json.put("fav", favorito);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlProducto, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    /*--------------------------------- Funciones personalizadas para la tabla servicios ---------------------------------*/

    /*
     * Método que inserta un nuevo servicios en la tabla Servicios
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertServicio(String name, String description, ImageView imageView) {
        JSONObject json = new JSONObject();
        String image = cuproducto.imageViewToString(imageView);

        try {
            json.put("name", name);
            json.put("description", description);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlServicio, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    /*
     * Método que consulta todos los servicios almacenados en la tabla servicios
     * */
    public void getAllServices(GridView gridView, ProgressBar progressBar) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServicio, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    ArrayList<Servicio> list = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        byte[] image = cuproducto.stringToByte(jsonObject.getString("image"));

                        Servicio servicio = new Servicio(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                image
                        );

                        list.add(servicio);
                    }

                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    ServicioAdapter servicioAdapter = new ServicioAdapter(context, list);
                    gridView.setAdapter(servicioAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    /*
     * Método que consulta los servicios relacionados con un ID
     * */
    public void getServicioById(String id, ImageView imageView, EditText name, EditText desc){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlServicio+"/"+id, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    byte[] image = cuservicio.stringToByte(jsonObject.getString("image"));

                    Servicio servicio = new Servicio(
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            image
                    );

                    byte[] imageP = servicio.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageP, 0, image.length);
                    imageView.setImageBitmap(bitmap);

                    name.setText(servicio.getName());
                    desc.setText(servicio.getDescription());

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /*
     * Método que elimina un servicio dependiendo del ID digitado
     * */
    public void deleteServicio(String id){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlServicio+"/"+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    /*
     * Método que actualiza el servicio indicado con el ID y la información diligenciada
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateServicio(String id, String name, String description,  ImageView imageView){
        JSONObject json = new JSONObject();
        String image = cuservicio.imageViewToString(imageView);

        try {
            json.put("id", id);
            json.put("name", name);
            json.put("description", description);
            json.put("image", image);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlServicio, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
}
