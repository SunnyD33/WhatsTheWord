package com.example.whatstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    private Button skipLoginButton;
    private Button newUserButton;
    private Button loginButton;
    private Vector<String> uNames = new Vector();
    private Vector<String> passwords = new Vector();
    private EditText user;
    private EditText password;

    public boolean loginStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uNames.add("qlw");
        passwords.add("1234");

        user = (EditText) findViewById(R.id.username);
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

    //TODO: Create function to have user login without credentials
    private void skipLogin()
    {
        Intent intent = new Intent(MainActivity.this, SearchScreen2.class);
        startActivity(intent);
        loginStatus = false;
    }

    //TODO: Create function to have user login with credentials
    private void login()
    {
        //TODO: check credentials vector before allowing to login

        for(int i = 0; i < uNames.size(); i++)
        {
            if(user.getText().toString() == uNames.get(i).toString())
            {
                for (int j = 0; j < passwords.size(); j++)
                {
                    if(password.getText().toString() == passwords.get(i).toString())
                    {
                        Intent intent = new Intent(MainActivity.this, SearchScreen.class);
                        startActivity(intent);
                    }
                }
            }
            else if(user.getText().toString() == uNames.get(i).toString())
            {
                for (int j = 0; j < passwords.size(); j++)
                {
                    if(password.getText().toString() != passwords.get(i).toString())
                    {
                        PasswordDialog passwordDialog = new PasswordDialog();
                        passwordDialog.show(getSupportFragmentManager(), "Password Dialog");
                    }
                }
            }
            else if(user.getText().toString() != uNames.get(i).toString())
            {
                for (int j = 0; j < passwords.size(); j++)
                {
                    if(password.getText().toString() == passwords.get(i).toString())
                    {
                        UsernameDialog usernameDialog = new UsernameDialog();
                        usernameDialog.show(getSupportFragmentManager(), "Username Dialog");
                    }
                }
            }
            else if(user.getText().toString() != uNames.get(i).toString())
            {
                for (int j = 0; j < passwords.size(); j++)
                {
                    if(password.getText().toString() != passwords.get(i).toString())
                    {
                        UsernamePasswordDialog upassDialog = new UsernamePasswordDialog();
                        upassDialog.show(getSupportFragmentManager(), "Username/Password Dialog");
                    }
                }
            }
        }
    }

    //TODO: Create function to have user create credentials
    private void newUserLogin()
    {

    }
}
