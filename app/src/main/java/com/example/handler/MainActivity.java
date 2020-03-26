package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_enter, btn_reset;
    EditText edit_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_enter = findViewById(R.id.btn_enter);
        btn_reset = findViewById(R.id.btn_reset);
        edit_pin = findViewById(R.id.edit_pin);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = edit_pin.getText().toString();
                if(pin.equals("1234")){
                    Intent togo = new Intent(getApplicationContext(), Home.class);
                    startActivity(togo);
                }

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),"Not implemented yet.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
