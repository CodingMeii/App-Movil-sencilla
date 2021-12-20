package com.example.jelouscrab.casosUso;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;

public class cuServicio {

    public String cursorToString(Cursor cursor){
        if(cursor.getCount() == 0){
            return "No hay datos";
        }else{
            StringBuffer buffer = new StringBuffer();

            while (cursor.moveToNext()){
                buffer.append("ID: "+cursor.getString(0)+"\n");
                buffer.append("NAME: "+cursor.getString(1)+"\n\n");
            }
            return buffer.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String imageViewToString(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = java.util.Base64.getEncoder().encodeToString(byteArray);

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] stringToByte(String string){
        byte[] result = java.util.Base64.getDecoder().decode(string);
        return result;
    }
}
