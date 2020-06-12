package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewUser extends AppCompatActivity {

    private Button cancelButton;
    private Button mCreateLoginButton;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Values for email and password entered by the user
        mEmail = findViewById(R.id.newEmail);
        mPassword = findViewById(R.id.newPassword);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLoginCreate();
            }
        });

        //TODO: create button for user to create new credentials
        mCreateLoginButton = findViewById(R.id.createLoginButton);
        mCreateLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required.");
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required.");
                }
            }
        });

    }

    private void CancelLoginCreate()
    {
        Intent intent = new Intent(NewUser.this,MainActivity.class);
        startActivity(intent);
    }
}
