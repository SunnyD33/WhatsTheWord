package com.example.whatstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    private Button skipLoginButton;
    private Button newUserButton;
    private Button loginButton;
    Vector credentials = new Vector();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //TODO: create button for user to login with credentials
        //TODO: check credentials vector before allowing to login
    }

    private void skipLogin()
    {
        Intent intent = new Intent(MainActivity.this, SearchScreen.class);
        startActivity(intent);
    }
}
