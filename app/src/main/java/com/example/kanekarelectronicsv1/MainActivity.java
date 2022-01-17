package com.example.kanekarelectronicsv1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText UserEmail,UserPass;
    Button LogInButton;
    TextView GoToSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserEmail = findViewById(R.id.userEmailAddress);
        UserPass = findViewById(R.id.userPassword);
        LogInButton = findViewById(R.id.login);
        GoToSignUp = findViewById(R.id.goToSignup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        GoToSignUp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, signupPage.class));
        });

        LogInButton.setOnClickListener(view -> {
                userLogin();
        });

    }

    private void userLogin()
    {
        final String email = UserEmail.getText().toString().trim();
        final String password = UserPass.getText().toString().trim();
        System.out.println(email + password);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.URL_LOGIN,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("tagconvertstr", "["+response+"]");
                        try {
                            JSONObject object = new JSONObject(response);
                            if(!object.getBoolean("error"))
                            {
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        object.getInt("id"),
                                        object.getString("Email"),
                                        object.getString("Name")


                                );
                                Toast.makeText(getApplicationContext(),"User Login Success",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.i("tagconvertstr", "["+e+"]");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email",email);
                params.put("Pass" , password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}