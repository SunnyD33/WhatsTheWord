package com.example.whatstheword;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    //Private variables
    private Button skipLoginButton;
    private Button newUserButton;
    private Button loginButton;
    //private Vector<String> uNames = new Vector();
    //private Vector<String> passwords = new Vector();
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //uNames.add("qlw");
        //passwords.add("1234");

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //TODO: create button for skipping login and going to the search menu
        skipLoginButton = (Button) findViewById(R.id.skipLoginButton);
        skipLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                skipLogin();
            }
        });

        //TODO: create button for a new user to create credentials for the application
        newUserButton = (Button) findViewById(R.id.newUserButton);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserLogin();
            }
        });

        //TODO: create button for user to login with credentials
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { login();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
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
