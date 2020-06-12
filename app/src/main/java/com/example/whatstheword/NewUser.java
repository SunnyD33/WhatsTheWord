package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewUser extends AppCompatActivity {

    private Button cancelButton;
    private Button mCreateLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar; //To be added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Values for email and password entered by the user
        mEmail = findViewById(R.id.newEmail);
        mPassword = findViewById(R.id.newPassword);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Toast values for successful login
        final Context context = getApplicationContext();
        final CharSequence text = "User Created!";
        final int duration = Toast.LENGTH_SHORT;

        //Toast values for unsuccessful login
        final Context context2 = getApplicationContext();
        final CharSequence text2 = "An Error Occurred..Please try again.";

        //TODO: create button for user to cancel logging in on new user screen
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLoginCreate();
            }
        });

        //TODO: create button for user to create new credentials
        mCreateLoginButton = findViewById(R.id.createLoginButton);
        mCreateLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6 && !TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password must be 6 or more characters");
                    return;
                }

                //Register user via Firebase
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast toast = Toast.makeText(context,text,duration);
                                    toast.show();
                                    Intent intent = new Intent(NewUser.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast toast = Toast.makeText(context2,text2,duration);
                                    toast.show();
                                }
                            }
                });
            }
        });

    }

    private void CancelLoginCreate()
    {
        Intent intent = new Intent(NewUser.this,MainActivity.class);
        startActivity(intent);
    }
}
