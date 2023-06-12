package com.example.project.location_advetise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    CheckBox check;
    int type;
    EditText etIpAddress, etUsername, etPassword, etEmail, etPhone;
    Button btRegister;
    RequestQueue queue;
    String ipAddress, username, password, email, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        queue = Volley.newRequestQueue(this);

        etIpAddress = findViewById(R.id.et_ip_address3);
        etUsername = findViewById(R.id.et_username2);
        etPassword = findViewById(R.id.et_password2);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btRegister = findViewById(R.id.bt_register);
        btRegister.setOnClickListener(v -> register());
        //check = findViewById(R.id.checkBox);


    }
    void register() {
        ipAddress = etIpAddress.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        phone = etPhone.getText().toString();
        if(check.isChecked())
        {
            type=1;
        }
        else
        {
            type=0;
        }

        if (ipAddress.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            makeSnackBar("All fields are necessary");
        } else {
            makeSnackBar("Wait for register");

            String url = "http://" + ipAddress + Constants.registerUrl + "username=" + username + "&password=" + password + "&Email=" + email + "&Phone=" + phone;// + "&type=" + type;
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    response -> {
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
            queue.cancelAll(0);
            queue.add(stringRequest);
        }
    }

    void goToMenuActivity(){
        Intent intent;
//        if(type==1) {
//            intent = new Intent(this, OwnerActivity.class);
//            intent.putExtra("ipAddress", ipAddress);
//            intent.putExtra("username", username);
//        }
//        else
//        {
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("ipAddress", ipAddress);
//        }
        startActivity(intent);
    }

    void makeSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}