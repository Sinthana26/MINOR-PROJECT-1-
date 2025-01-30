package com.luxand.facerecognition;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity implements View.OnClickListener{
    Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btn1= findViewById(R.id.button);
        btn2= findViewById(R.id.button2);




        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.button:

                startActivity(new Intent(getApplicationContext(), SudentLogin.class));
                Toast.makeText(this, "Student page", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button2:
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
                Toast.makeText(this, "Admin Page", Toast.LENGTH_SHORT).show();
                break;


        }
    }



}
