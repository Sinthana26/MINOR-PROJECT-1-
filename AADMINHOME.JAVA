package com.luxand.facerecognition;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity implements View.OnClickListener{
    Button  btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btn2= findViewById(R.id.button2);
        btn3= findViewById(R.id.button3);



          btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){




            case R.id.button2:
                startActivity(new Intent(getApplicationContext(), ViewAttendance.class));
                //   Toast.makeText(this, "Employee Page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //  Toast.makeText(this, "Bunk Update Page", Toast.LENGTH_SHORT).show();
                break;


        }
    }



}
