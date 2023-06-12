package com.example.project.location_advetise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etIpAddress, etUsername, etPassword;
    Button btLogin,btRegister;
    String Url;
    Context context = this;
    int type=0,shop_type;
    CheckBox check,check1,check2,check3,check4;

    LinearLayout check_layout;

    RequestQueue queue;
    String ipAddress, username, password, trolley_id = "101";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        initAllWidgets();
    }

    void initAllWidgets(){
        etIpAddress = findViewById(R.id.et_ip_address);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_login);
        btRegister = findViewById(R.id.bt_register2);
        btLogin.setOnClickListener(view -> {
            if (check1.isChecked()) shop_type=4;
            if (check2.isChecked()) shop_type=3;
            if (check3.isChecked()) shop_type=2;
            if (check4.isChecked()) shop_type=1;
            login();
        });


        btRegister.setOnClickListener(v -> register());
     check = findViewById(R.id.checkBox);

        check_layout = findViewById(R.id.check_layout);
        check1 = findViewById(R.id.checkBox2);
        check2 = findViewById(R.id.checkBox3);
        check3 = findViewById(R.id.checkBox4);
        check4 = findViewById(R.id.checkBox5);


        check.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            // Check which checkbox was clicked
            if (checked){
                type=1;
                check_layout.setVisibility(View.VISIBLE);
                // Do your coding
            }
            else{
                type=0;
                check_layout.setVisibility(View.GONE);
                // Do your coding
            }
        });
    }
    void register()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void login() {


        ipAddress = etIpAddress.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (ipAddress.isEmpty() || username.isEmpty() || password.isEmpty()) {
            makeSnackBar("All fields are necessary");
        } else {
            makeSnackBar("Wait for login");
            String url="";
            if (type==0) {
                url = "http://" + ipAddress+ Constants.login_url + "username=" + username + "&password=" + password ;
            }
            else
            {
                url = "http://" + ipAddress + Constants.login_owner_url + "username=" + username + "&password=" + password + "&shop_type=" + shop_type;
            }
            Log.d("url: ",url);
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    (String response) -> {
                        Log.d("response: ",response);
                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.getBoolean("success")) {
                                makeToast(object.getString("message"));
                                goToMenuActivity();

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
    void goToMenuActivity(){
        Intent intent;
        if(type==1) {
            intent = new Intent(this, OwnerActivity.class);
            intent.putExtra("ipAddress", ipAddress);
            intent.putExtra("username", username);
            intent.putExtra("shop_type", shop_type);
        }
        else{
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("ipAddress", ipAddress);
        }
        startActivity(intent);
    }

    void makeSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}