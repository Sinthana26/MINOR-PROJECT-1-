package com.luxand.facerecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SudentLogin extends AppCompatActivity {
    Button dl;
    EditText dlt,dlp;
    ProgressDialog pDialog;
    public static String name="";
    SharedPreferences sp1;
    public static final String SHARED_PREFS1 = "";
    // initializing
    // FusedLocationProviderClient
    // object
    public static  String latitude="";
    public static String longitude="";
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    EditText latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudent_login);
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);
        sp1 = getSharedPreferences(SHARED_PREFS1, Context.MODE_PRIVATE);
        dlt = (EditText) findViewById(R.id.login_username);
        dlp =(EditText) findViewById(R.id.login_password);
        dl= (Button) findViewById(R.id.login_button);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitudeTextView.getText().toString().equals("")||longitTextView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Turn On Your Location", Toast.LENGTH_SHORT).show();

                }else {
                    new dlogin().execute();
                }
            }
        });


    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled

            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            String lat = latitudeTextView.getText().toString();
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            String lon = longitTextView.getText().toString();
            latitude=lat;
            longitude=lon;
            Log.i("latitude", latitude);
            Log.i("latitude", latitude);
        }
    };
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
    public class dlogin extends AsyncTask<String, String, String> {

        final String duname = dlt.getText().toString();
        final String  dpwd = dlp.getText().toString();


        //  String  gender = selected_gender.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SudentLogin.this);
            pDialog.setMessage("Requesting " + duname + "...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @SuppressWarnings("deprecation")

        protected String doInBackground(String... args) {

            String txt = "";
            try {
                String ur = "http://"+ServerConnect.serverip +"/Android/faceattendancelocation/studentlogin.php?uname=" +duname+"&password="+dpwd
                        +"&latitude="+ URLEncoder.encode(latitudeTextView.getText().toString()) +"&longitude="+ URLEncoder.encode(longitTextView.getText().toString());
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
            String ss= file_url.trim();
            Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_LONG).show();

            if (file_url.trim().equals("Success")) {

              //  Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_LONG).show();
                Intent in = new Intent(SudentLogin.this, MainActivity2.class);
                SharedPreferences.Editor editor = sp1.edit();
                latitude=latitudeTextView.getText().toString();
                longitude=longitTextView.getText().toString();
                name=duname;
                editor.putString("EMAIL_KEY1",duname);
                editor.putString("AID",duname);
                editor.commit();
                in.putExtra("dun",duname);
                startActivity(in);
                Log.i("latitude", latitude);
                Log.i("latitude", longitude);
            }


            else if(file_url.trim().equals("failed")) {
                Toast.makeText(getApplicationContext(), "User name and password wrong", Toast.LENGTH_LONG).show();

                dlt.setText("");
                dlp.setText("");

            }
            else
            { Toast.makeText(getApplicationContext(), "Connet error", Toast.LENGTH_LONG).show();}

            pDialog.dismiss();
        }
    }


}
