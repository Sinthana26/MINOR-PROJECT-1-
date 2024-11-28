package com.luxand.facerecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendance extends AppCompatActivity {
    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;

    String HTTP_JSON_URL = "http://"+ ServerConnect.serverip+"/Android/faceattendancelocation/viewattendance.php";

    String Image_Name_JSON = "fname";
    String Image_URL_JSON = "grade2";
    String Image_Type = "ptype";
    String Image_Category = "stax";

    String Image_Price = "fcount";
    String Image_Tax = "grade3";

    String PID = "grade3";

    JsonArrayRequest RequestOfJSonArray ;

    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> Product_Name;
    ArrayList<String> Product_Price;
    ArrayList<String> Product_Type;
    ArrayList<String> Product_Category;
    ArrayList<String> Product_Quantity;
    ArrayList<String> Product_Tax;
    ArrayList<String> Product_Id;
    ArrayList<String> Product_Description;

    ArrayList<String> Product_ImageURL;


    public static final String SHARED_PREFS1 = "";

    SharedPreferences sharedpreferences1;
    TextView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        sharedpreferences1 = getSharedPreferences(SHARED_PREFS1, Context.MODE_PRIVATE);
        Product_Name = new ArrayList<>();
        Product_Price = new ArrayList<>();
        Product_Type = new ArrayList<>();
        Product_Category = new ArrayList<>();
        Product_Quantity = new ArrayList<>();
        Product_Tax = new ArrayList<>();
        Product_Id = new ArrayList<>();
        Product_Description = new ArrayList<>();

        Product_ImageURL = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview10);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

//        v=findViewById(R.id.vac);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent nlog = new Intent(UserProduct.this, Bill.class);
//                startActivity(nlog);
//            }
//        });

        JSON_HTTP_CALL();

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(ViewAttendance.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                Log.i("TAG", HTTP_JSON_URL);

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

//                    Intent ii = new Intent(ViewFood.this,OneFood.class);
//                    ii.putExtra("pid",Product_Id.get(RecyclerViewItemPosition));
//                    ii.putExtra("pname",Product_Name.get(RecyclerViewItemPosition));
//                    ii.putExtra("pimg",Product_ImageURL.get(RecyclerViewItemPosition));
//                    ii.putExtra("pcategory",Product_Category.get(RecyclerViewItemPosition));
//                    ii.putExtra("ptype",Product_Type.get(RecyclerViewItemPosition));
//                    ii.putExtra("stax",Product_Tax.get(RecyclerViewItemPosition));
//                    ii.putExtra("pprice",Product_Price.get(RecyclerViewItemPosition));
//
//                    startActivity(ii);

                    // alertDialog(ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition),Price.get(RecyclerViewItemPosition),
                    //       Itemid.get(RecyclerViewItemPosition),Phone.get(RecyclerViewItemPosition),FID.get(RecyclerViewItemPosition));
                    //ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                    // Price.add(json.getString(Image_Price));
                    // Itemid.add(json.getString(GID));

                    // Showing RecyclerView Clicked Item value using Toast.
                    // Toast.makeText(UserHome.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }


    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(ViewAttendance.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(""+json.getString(Image_Name_JSON));
                GetDataAdapter2.setImageType(""+json.getString(Image_Price));
                GetDataAdapter2.setImageSize(""+json.getString(Image_Category));
                GetDataAdapter2.setImagePack(""+json.getString(Image_Type));
                GetDataAdapter2.setImagename(""+json.getString(Image_URL_JSON));
                GetDataAdapter2.setImagePrice(""+json.getString(Image_Tax));
                GetDataAdapter2.setIMGID(json.getString(PID));
                GetDataAdapter2.setImageUrl("http://"+ServerConnect.serverip+"/Android/facestudentattendance/upload/"+json.getString(Image_URL_JSON)+".jpg");
                // Adding image title name in array to display on RecyclerView click event.


                Product_Name.add(json.getString(Image_Name_JSON));
                Product_Price.add(json.getString(Image_Price));
                Product_Type .add(json.getString(Image_Type));
                Product_Category.add(json.getString(Image_Category));
                Product_Tax.add(json.getString(Image_Tax));

                Product_Id.add(json.getString(PID));
                Product_ImageURL.add(json.getString(Image_URL_JSON));







            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerBillAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }



}
