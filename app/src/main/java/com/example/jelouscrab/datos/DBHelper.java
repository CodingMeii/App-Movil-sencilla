package com.example.jelouscrab.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "reto4.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRODUCTOS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR," +
                "DESCRIPTION VARCHAR," +
                "PRICE VARCHAR," +
                "IMAGE BLOB)");

        db.execSQL("CREATE TABLE SERVICIOS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME VARCHAR," +
                "DESCRIPTION VARCHAR," +
                "IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTOS");
        db.execSQL("DROP TABLE IF EXISTS SERVICIOS");
        onCreate(db);
    }

    //Funciones personalizadas para productos

    /*
    * Método que inserta un nuevo producto en la tabla Productos
    * */
    public void insertProductos(String name, String desc, String price, byte[] image){
        String sql = "INSERT INTO PRODUCTOS VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, desc);
        statement.bindString(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }

    /*
    * Método que consulta todos los productos almacenados en la tabla productos
    * */
    public Cursor getProductos(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTOS", null);
        return  cursor;
    }

    /*
     * Método que consulta los productos relacionados con un ID
     * */
    public Cursor getProductoById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTOS WHERE ID="+id, null);
        return  cursor;
    }

    /*
    * Método que elimina un profucto dependiendo del ID digitado
    * */
    public void deleteProducto(String id){
        String[] args = new String[]{id};
        sqLiteDatabase.delete("PRODUCTOS", "ID=?", args);
    }

    /*
    * Método que actualiza el producto indicado con el ID y la información diligenciada
    * */
    public void updateProductos(String id, String name, String desc, String price, byte[] image){
        String sql = "UPDATE PRODUCTOS " +
                "SET NAME =?," +
                "DESCRIPTION = ?," +
                "PRICE = ?," +
                "IMAGE = ?" +
                "WHERE ID="+id;
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, desc);
        statement.bindString(3, price);
        statement.bindBlob(4, image);

        statement.executeUpdateDelete();
    }

    //----------------------Funciones personalizadas para servicios----------------------

    /*
     * Método que inserta un nuevo servicio en la tabla Servicios
     * */
    public void insertServicios(String name, String desc, byte[] image){
        String sql = "INSERT INTO SERVICIOS VALUES(null, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, desc);
        statement.bindBlob(3, image);

        statement.executeInsert();
    }

    /*
     * Método que consulta todos los servicios almacenados en la tabla servicios
     * */
    public Cursor getServicios(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SERVICIOS", null);
        return  cursor;
    }

    /*
     * Método que consulta los SERVICIOS relacionados con un ID
     * */
    public Cursor getServicioById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SERVICIOS WHERE ID="+id, null);
        return  cursor;
    }

    /*
     * Método que elimina un servicio dependiendo del ID digitado
     * */
    public void deleteServicio(String id){
        String[] args = new String[]{id};
        sqLiteDatabase.delete("SERVICIOS", "ID=?", args);
    }

    /*
     * Método que actualiza el servicio indicado con el ID y la información diligenciada
     * */
    public void updateServicios(String id, String name, String desc, byte[] image){
        String sql = "UPDATE SERVICIOS " +
                "SET NAME =?," +
                "DESCRIPTION = ?," +
                "IMAGE = ?" +
                "WHERE ID="+id;
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, desc);
        statement.bindBlob(3, image);

        statement.executeUpdateDelete();
    }
}
