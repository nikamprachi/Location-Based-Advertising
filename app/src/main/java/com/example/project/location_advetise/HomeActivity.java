package com.example.project.location_advetise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Button btn_sta,btn_med,btn_ele,btn_res;
    RequestQueue queue;

    String ipAddress,ShopType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_sta = findViewById(R.id.stationary_btn);
        btn_med = findViewById(R.id.medical_btn);
        btn_ele = findViewById(R.id.electrical_btn);
        btn_res = findViewById(R.id.restaurant_btn);
        queue = Volley.newRequestQueue(this);

        btn_sta.setOnClickListener(view -> {
            ShopType = "stationary";
            goToMenuActivity();

        });
        btn_med.setOnClickListener(view -> {
            ShopType = "medic";
            goToMenuActivity();

        });
        btn_ele.setOnClickListener(view -> {
            ShopType = "electrical";
            goToMenuActivity();

        });
        btn_res.setOnClickListener(view -> {
            ShopType = "restaurant";
            goToMenuActivity();

        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ipAddress = extras.getString("ipAddress");  // if not returns null
            if (ipAddress == null) {
                makeSnackBar("invalid data passed");
            }
        }

    }

        void makeSnackBar (String message){
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
        void makeToast (String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        void goToMenuActivity () {
            Intent intent = new Intent(this, ShopsActivity.class);
            intent.putExtra("ipAddress", ipAddress);
            intent.putExtra("ShopType",ShopType);
            startActivity(intent);
        }

}