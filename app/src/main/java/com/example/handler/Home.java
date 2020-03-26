package com.example.handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class Home extends AppCompatActivity {

    ImageView image1,image2,temp;
    Bitmap img1,img2,bitTemp;
    Button btn_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        btn_select = findViewById(R.id.btn_select);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(image1,img1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(image2,img2);
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                save_to_DB();
            }
        });
    }

    private static final int PICK_IMAGE = 100;

    private void openGallery(ImageView imgViewer, Bitmap bitmap) {
        temp = imgViewer;
        bitTemp = bitmap;
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            temp.setImageURI(imageUri);
            try {
                bitTemp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                save_to_DB(bitTemp);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"error: "+e,Toast.LENGTH_LONG).show();
            }
        }
    }

    void save_to_DB(Bitmap bitmap){
        SharedPreferences prefs = getSharedPreferences("SharedInfo", MODE_PRIVATE);
        String dbName = prefs.getString("dbName", "");
        String tableName = prefs.getString("tableName", "");
        if(!(dbName.equals("") || tableName.equals(""))){

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
            byte[] imageByte = stream.toByteArray();
            String str = new String(imageByte);

            try {
                SQLiteDatabase mydatabase = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                mydatabase.execSQL("INSERT INTO " + tableName + " VALUES('img1','"+ str + "');");
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"DB: "+e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"No DB name or Table name!\ndbName: "+dbName+
                    "\ntableName: "+tableName,Toast.LENGTH_LONG).show();
        }
    }
}
