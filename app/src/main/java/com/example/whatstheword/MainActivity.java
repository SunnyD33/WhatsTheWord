package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    //Private variables
    private Button mSkipLoginButton;
    private Button mNewUserButton;
    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar; //To be added


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Set values for email and password from xml layouts
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        //TODO: create button for skipping login and going to the search menu
        mSkipLoginButton = findViewById(R.id.skipLoginButton);
        mSkipLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                skipLogin();
            }
        });

        //TODO: create button for a new user to create credentials for the application
        mNewUserButton = findViewById(R.id.newUserButton);
        mNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                newUserLogin();
            }
        });

        //TODO: create button for user to login with credentials
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
        //updateUI(user);
    }

    //TODO: Create function to have user login without credentials
    private void skipLogin()
    {
        Intent intent = new Intent(MainActivity.this, SearchScreen2.class);
        startActivity(intent);
    }

    //TODO: Create function to have user login with credentials
    private void login()
    {

    }

    //TODO: Create function to have user create credentials
    private void newUserLogin()
    {
        Intent intent = new Intent(MainActivity.this,NewUser.class);
        startActivity(intent);
    }

}
