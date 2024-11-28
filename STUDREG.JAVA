package com.luxand.facerecognition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class StudentRegister extends AppCompatActivity {
    EditText e1,e3,e7,e10,e12,e13,e14;
    TextView e2;
    RadioGroup ra;
    Button bb;

    ProgressDialog pDialog;
    RadioButton selected_gender;
    String gender;
    DatePickerDialog datePickerDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        e1 = (EditText) findViewById(R.id.pname);
        e2 = (TextView) findViewById(R.id.eemail);
        e3 = (EditText) findViewById(R.id.pcontact);
        e7 = (EditText) findViewById(R.id.edob);
        e12=(EditText)findViewById(R.id.eemail1);
        e10 = (EditText) findViewById(R.id.epassword);
        e13=findViewById(R.id.lat);
        e14=findViewById(R.id.lang);
        ra= (RadioGroup)findViewById(R.id.prrg);
        e2.setText(ProcessImageAndDrawResults1.userName);
        bb = (Button) findViewById(R.id.preg);



        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear        = c.get(Calendar.YEAR); // current year
                int mMonth       = c.get(Calendar.MONTH); // current month
                int mDay         = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(StudentRegister.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e7.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String dname = e1.getText().toString();

                final String demail = e2.getText().toString();
                final String dcontact = e3.getText().toString();

                final String ddob = e7.getText().toString();

                final String dpwd = e10.getText().toString();

                if(dname.equals("")||demail.equals("")||dcontact.equals("")
                        ||ddob.equals("")||dpwd.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill All Fields",Toast.LENGTH_LONG).show();
                }else {


                    int checkedId = ra.getCheckedRadioButtonId();
                    selected_gender = (RadioButton) findViewById(checkedId);
                    if (checkedId == -1) {
                        Toast.makeText(StudentRegister.this, "Select gender please", Toast.LENGTH_SHORT).show();
                    } else {
                        gender = selected_gender.getText().toString();
                        new registeration().execute();
                    }
                }


            }
        });

    }



    public class registeration extends AsyncTask<String, String, String> {

        final String dname = e1.getText().toString();
        final String demail = e2.getText().toString();
        final String dcontact = e3.getText().toString();
        final String ddob = e7.getText().toString();

        final String email = e12.getText().toString();


        final String dpwd = e10.getText().toString();
        final String lat = "11.054475";
        final String lang = "78.048403";

        //  String  gender = selected_gender.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StudentRegister.this);
            pDialog.setMessage("Requesting " + dname + ")...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @SuppressWarnings("deprecation")

        protected String doInBackground(String... args) {

            String txt = "";
            try {
                String ur="http://"+ServerConnect.serverip+"/Android/faceattendancelocation/studentregister.php?pname="+ URLEncoder.encode(dname)
                        +"&pemail="+ URLEncoder.encode(demail)
                        +"&pcontact="+URLEncoder.encode(dcontact)+
                        "&email="+URLEncoder.encode(email)+
                        "&gender="+URLEncoder.encode(gender)+
                        "&edob="+URLEncoder.encode(ddob)
                        +"&epassword="+URLEncoder.encode(dpwd) +"&lat="+URLEncoder.encode(lat)
                        +"&lang="+URLEncoder.encode(lang);
                Log.i("URL", ur);
                URL url = new URL(ur);
                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                DataInputStream dis = new DataInputStream(uc.getInputStream());
                String t = "";
                while ((t = dis.readLine()) != null) {
                    txt += t;
                }
                Log.i("Read", txt);
                dis.close();
            } catch (Exception e) {
                Log.i("Login Ex", e.getMessage());
            }
            return txt;
        }


        protected void onPostExecute(String file_url) {
            // String ss= file_url.trim();
            // Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_LONG).show();

            if (file_url.trim().equals("successfully")) {


                Toast.makeText(getApplicationContext(), "Registration Success!", Toast.LENGTH_LONG).show();
                finish();
                Intent in = new Intent(getApplicationContext(), AdminLogin.class);
                // in.putExtra("si",serverip);
                startActivity(in);

            }


            else if(file_url.trim().equals("User name allready used type another one")) {
                Toast.makeText(getApplicationContext(), "User name allready used type another one", Toast.LENGTH_LONG).show();


                e10.setText("");

            }
            else
            { Toast.makeText(getApplicationContext(), "Please Check Login...!", Toast.LENGTH_LONG).show();}

            pDialog.dismiss();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.set_main) {

            Intent lo=new Intent(getApplicationContext(),ServerConnect.class);
            startActivity(lo);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

    }

}
