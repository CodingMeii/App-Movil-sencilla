package com.example.jelouscrab.forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jelouscrab.R;
import com.example.jelouscrab.casosUso.cuProducto;
import com.example.jelouscrab.casosUso.cuServicio;
import com.example.jelouscrab.datos.ApiOracle;
import com.example.jelouscrab.datos.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class addServicio extends AppCompatActivity {
    private Button btnInsertar, btnConsultar, btnEliminar, btnActualizar, btnCambiar, btnLimpiar;
    private EditText edtId, edtName, edtDesc, edtPrice;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICATION_ID = 0;
    private ImageView selectImg;

    /*------ SQLite ------*/
    //private DBHelper dbHelper;

    private ApiOracle apiOracle;
    private cuServicio cuservicio;

    private final static int READ_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_servicio);

        //Layout
        LinearLayout linearLayout;
        linearLayout = (LinearLayout) findViewById(R.id.MainService2);

        //Buttons
        btnInsertar = (Button) findViewById(R.id.btnInsertar2);
        btnConsultar = (Button) findViewById(R.id.btnConsultar2);
        btnActualizar = (Button) findViewById(R.id.btnActualizar2);
        btnEliminar = (Button) findViewById(R.id.btnEliminar2);
        btnCambiar = (Button) findViewById(R.id.btnCambiar2);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar2);

        //EditTexts
        edtId = (EditText) findViewById(R.id.edtId2);
        edtName = (EditText) findViewById(R.id.edtName2);
        edtDesc = (EditText) findViewById(R.id.edtDesc2);

        //ImageView
        selectImg = (ImageView) findViewById(R.id.selectImg2);

        /*------ SQLite ------*/
        //dbHelper = new DBHelper(getApplicationContext());

        /*------ Api Oracle ------*/
        apiOracle = new ApiOracle(getApplicationContext());
        cuservicio = new cuServicio();

        //Funciones bonotes
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        addServicio.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    //Toast.makeText(getApplicationContext(), edtName.getText().toString().trim()+" "+edtDesc.getText().toString().trim(), Toast.LENGTH_LONG).show();

                    String name = edtName.getText().toString().trim();
                    String desc = edtDesc.getText().toString().trim();

                    if (name.equals("") || desc.equals("")){
                        Toast.makeText(getApplicationContext(), "Faltan campos por llenar", Toast.LENGTH_LONG).show();
                    }else{
                        /*------ SQLite ------*/
                        //dbHelper.insertServicios(name, desc, imageViewToByte(selectImg));

                        /*------ Api Oracle ------*/
                        apiOracle.insertServicio(name, desc, selectImg);

                        Snackbar snackbar_insertar = Snackbar.make(linearLayout,"Servicio insertado.",Snackbar.LENGTH_LONG);
                        snackbar_insertar.show();
                        limpiarCampos();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();

                if(id.equals("")){
                    /*------ SQLite ------*/
                    //Cursor cursor = dbHelper.getServicios();
                    //Toast.makeText(getApplicationContext(), cuservicio.cursorToString(cursor), Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(), "Los campos no están completos.", Toast.LENGTH_LONG).show();
                }else{
                    /*------ SQLite ------*/
                    //Cursor cursor = dbHelper.getServicioById(id);
                    //showById(cursor);

                    /*------ Api Oracle ------*/
                    apiOracle.getServicioById(id, selectImg, edtName, edtDesc);
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addServicio.this);
                builder.setTitle("Actualizar servicio");

                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();

                if (id.equals("") || name.equals("") || desc.equals("")){
                    Toast.makeText(getApplicationContext(), "Los campos no están completos", Toast.LENGTH_LONG).show();
                }else{
                    builder.setMessage("Está seguro de actualizar el servicio").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                /*------ SQLite ------*/
                                //dbHelper.updateServicios(id, name, desc, imageViewToByte(selectImg));

                                /*------ Api Oracle ------*/
                                apiOracle.updateServicio(id, name, desc, selectImg);

                                Snackbar snackbar_insertar = Snackbar.make(linearLayout,"Servicio actualizado.",Snackbar.LENGTH_LONG);
                                snackbar_insertar.show();
                                limpiarCampos();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "No se ha actualizado el servicio", Toast.LENGTH_LONG).show();
                        }
                    }).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(addServicio.this);
                builder.setTitle("Eliminar servicio");
                builder.setMessage("Está seguro de eliminar el servicio").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(id.equals("")){
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            limpiarCampos();
                        }else {
                            /*------ SQLite ------*/
                            //dbHelper.deleteServicio(id);

                            /*------ Api Oracle ------*/
                            apiOracle.deleteServicio(id);

                            createNotificationChannel();
                            createNotification("JelousCrab", "Se ha eliminado el servicio " + id);
                            //Toast.makeText(getApplicationContext(), "Se ha eliminado el producto "+id, Toast.LENGTH_LONG).show();
                            limpiarCampos();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "No se ha eliminado el servicio "+id, Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });
    }

    //Creacion del canal de la notificacion
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Metodo Creacion de notificacion
    public void createNotification(String titulo, String contenido){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.jc);
        builder.setContentTitle(titulo);
        builder.setContentText(contenido);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void limpiarCampos(){
        edtId.setText("");
        edtName.setText("");
        edtDesc.setText("");
        selectImg.setImageResource(R.drawable.gallery);
    }

    public void showById(Cursor cursor){
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "Servicio no encontrado", Toast.LENGTH_LONG).show();
        }else{
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()){
                edtId.setText(cursor.getString(0));
                edtName.setText(cursor.getString(1));
                edtDesc.setText(cursor.getString(2));
                byte[] image = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                selectImg.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == READ_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, READ_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin permisos", Toast.LENGTH_LONG).show();
            }return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == READ_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                selectImg.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}