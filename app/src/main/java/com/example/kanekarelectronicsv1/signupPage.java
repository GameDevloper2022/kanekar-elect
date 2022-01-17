package com.example.kanekarelectronicsv1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signupPage extends AppCompatActivity {
    EditText SignupUserEmail,SignupUserPass,SignupUserName,SignupUserConfirmPass;
    Button SignUpButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        SignupUserEmail = findViewById(R.id.signupUserEmail);
        SignupUserName = findViewById(R.id.userName);
        SignupUserPass = findViewById(R.id.signupUserPassword);
        SignupUserConfirmPass = findViewById(R.id.confirmLoginPassword);

        SignUpButton = findViewById(R.id.signup);

        progressDialog = new ProgressDialog(this);

        SignUpButton.setOnClickListener(view -> regiserUser());
    }

    private  void regiserUser(){
        final String email = SignupUserEmail.getText().toString().trim();
        final String name = SignupUserName.getText().toString().trim();
        final String password = SignupUserPass.getText().toString().trim();

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    System.out.println(jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Name",name);
                params.put("Email",email);
                params.put("Pass",password);
                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}