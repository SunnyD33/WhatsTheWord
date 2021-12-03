package com.example.whatstheword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewUser extends AppCompatActivity {

    private Button cancelButton;
    private Button mCreateLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar; //To be added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        getSupportActionBar().setTitle("Create New User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Values for email, password, first name and last name entered by the user
        mEmail = findViewById(R.id.newEmail);
        mPassword = findViewById(R.id.newPassword);
        mFirstName = findViewById(R.id.userFirstName);
        mLastName = findViewById(R.id.userLastName);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Toast values for successful sign-up
        final Context context = getApplicationContext();
        final CharSequence text = "User Created!";
        final int duration = Toast.LENGTH_SHORT;

        //Toast values for unsuccessful sign-up
        final Context context2 = getApplicationContext();
        final CharSequence text2 = "Error: Email already in use";

        //TODO: create button for user to create new credentials
        mCreateLoginButton = findViewById(R.id.createLoginButton);
        mCreateLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be 6 or more characters");
                    return;
                }

                if (TextUtils.isEmpty(firstName))
                {
                    mFirstName.setError("First Name required");
                    return;
                }

                if (TextUtils.isEmpty(lastName))
                {
                    mLastName.setError("Last Name required");
                    return;
                }

                //Register user via Firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Display message to user that login creation was successful
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            //Values for database storage in Firebase
                            String userID = mAuth.getCurrentUser().getUid();
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            String userFirstName = mFirstName.getText().toString();
                            String userLastName = mLastName.getText().toString();

                            //Database references for storage in Firebase
                            DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                            //HashMap for storing extra values in the DB
                            Map newPost = new HashMap();
                            newPost.put("Email", userEmail);
                            newPost.put("First Name", userFirstName);
                            newPost.put("Last Name", userLastName);

                            userDB.setValue(newPost);

                            //Switch to search screen after successful login
                            Intent intent = new Intent(NewUser.this, SearchScreen.class);
                            startActivity(intent);
                        }
                        else
                        {
                            //Display error message indicating login was unsuccessful
                            Toast toast = Toast.makeText(context2, text2, duration);
                            toast.show();
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
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        AlertDialog cancelDialog = new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(NewUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
        cancelDialog.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            AlertDialog cancelDialog = new AlertDialog.Builder(this)
                    .setTitle("Cancel")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(NewUser.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
            cancelDialog.create();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
