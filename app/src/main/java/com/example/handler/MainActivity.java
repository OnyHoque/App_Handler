package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btn_enter, btn_reset;
    EditText edit_pin;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_enter = findViewById(R.id.btn_enter);
        btn_reset = findViewById(R.id.btn_reset);
        edit_pin = findViewById(R.id.edit_pin);


        if(flag){
            flag =false;
            check_data();
            Req_Permission();
        }


        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = edit_pin.getText().toString();
//                if(pin.equals("1234")){
//                    Intent togo = new Intent(getApplicationContext(), Home.class);
//                    startActivity(togo);
//                }
                Intent togo = new Intent(getApplicationContext(), Home.class);
                startActivity(togo);

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),"Not implemented yet.",Toast.LENGTH_SHORT);
                toast.show();
//                try{
//                    check_data();
//                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_LONG).show();
//                }

            }
        });
    }

    private void Req_Permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    void check_data(){
        SharedPreferences prefs = getSharedPreferences("SharedInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int newStart = prefs.getInt("newStart", 0);
        if(newStart == 0){
            Toast.makeText(getApplicationContext(),"New start",Toast.LENGTH_SHORT).show();
            editor.putInt("newStart", 1);
            editor.putString("dbName","myDB");
            editor.putString("tableName","table1");
            SQLiteDatabase mydatabase = openOrCreateDatabase("myDB",MODE_PRIVATE,null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS table1(name VARCHAR,image VARCHAR);");
            editor.apply();
        }else{
            Toast.makeText(getApplicationContext(),"Already started",Toast.LENGTH_SHORT).show();
        }
    }
}
