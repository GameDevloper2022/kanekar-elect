package com.example.kanekarelectronicsv1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        GoToSignUp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, signupPage.class));
        });

    }
}