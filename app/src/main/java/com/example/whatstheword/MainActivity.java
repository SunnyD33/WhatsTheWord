package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Set values for email and password from xml layouts
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        //TODO: create button for skipping login and going to the search menu
        //Private variables
        TextView mSkipLoginButton = findViewById(R.id.skipLoginButton);
        mSkipLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipLogin();
            }
        });

        //TODO: create button for a new user to create credentials for the application
        Button mNewUserButton = findViewById(R.id.newUserButton);
        mNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserLogin();
            }
        });

        //TODO: create button for user to login with credentials
        Button mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                //Sign in existing users
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Successful sign in message
                            Toast.makeText(getApplicationContext(), "Signing in..", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = mAuth.getCurrentUser();

                            //Take user to search screen
                            Intent intent = new Intent(MainActivity.this, SearchScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //Unsuccessful login message
                            Toast.makeText(getApplicationContext(), "Email or Password incorrect! Please try again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(MainActivity.this, SearchScreen.class));
            finish();
        }
    }

    //TODO: Create function to have user login without credentials
    private void skipLogin() {
        Intent intent = new Intent(MainActivity.this, SearchScreen2.class);
        startActivity(intent);
        finish();
    }

    //TODO: Create function to have user create credentials
    private void newUserLogin() {
        Intent intent = new Intent(MainActivity.this, NewUser.class);
        startActivity(intent);
        finish();
    }

}
