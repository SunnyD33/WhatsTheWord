package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SearchScreen extends AppCompatActivity {

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    private void logout() {
        //Toast values for user logout
        final Context context = getApplicationContext();
        final CharSequence text = "Logging out";
        final int duration = Toast.LENGTH_SHORT;

        //Sign user out of Firebase
        FirebaseAuth.getInstance().signOut();

        //Display message to user that they have logged out
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();

        //Switch to login screen
        Intent intent = new Intent(SearchScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
