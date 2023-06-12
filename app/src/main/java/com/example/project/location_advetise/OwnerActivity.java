package com.example.project.location_advetise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class OwnerActivity extends AppCompatActivity {
    TextView owner;
    EditText advt;
    String ipAddress,username,advt_data;
    int shop_type;
    Button upload;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        owner = findViewById(R.id.textview_owner);
        advt = findViewById(R.id.editTextTextMultiLine3);
        upload = findViewById(R.id.button2);

        queue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ipAddress = extras.getString("ipAddress");  // if not returns null
            if (ipAddress == null) {
                makeSnackBar("invalid data passed");
            }
            username = extras.getString("username");  // if not returns null
            if (username == null) {
                makeSnackBar("invalid data passed");
            }
            owner.setText(username);
            shop_type = extras.getInt("shop_type");  // if not returns null

        }

        upload.setOnClickListener(v -> upload_data() );


    }
    void upload_data() {
        advt_data = advt.getText().toString();

        if (advt_data.isEmpty() ) {
            makeSnackBar("Please fill advertising data");
        } else {
            makeSnackBar("Wait for upload");

            String url = "http://" + ipAddress + Constants.upload_advt + "username=" + username + "&advt=" + advt_data + "&shop_type=" + shop_type;
            Log.d("url: ",url);
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    (String response) -> {
                        Log.d("response: ",response);
                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getBoolean("success")) {
                                makeSnackBar(object.getString("message"));
                            }
                            else makeSnackBar(object.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            makeSnackBar("parse error");
                        }
                    },
                    error -> makeSnackBar("Request failed, check server reachable."));
            //queue.cancelAll(0);
            queue.add(stringRequest);
            Log.d("stringRequest: ",String.valueOf(stringRequest));
        }
    }
    void makeSnackBar (String message){
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
    void makeToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}