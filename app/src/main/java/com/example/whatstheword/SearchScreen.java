package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SearchScreen extends AppCompatActivity {

    private Button add_favorites;
    private String url;
    public TextView defBox;
    public EditText enterWord;
    private String favoriteWord;
    private Button clear_button;
    private String definition;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        defBox = (TextView) findViewById(R.id.definitionBox);
        enterWord = (EditText) findViewById(R.id.search_bar);

        clear_button = (Button) findViewById(R.id.clear_button);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        add_favorites = (Button) findViewById(R.id.add_to_favorites);
        add_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });
    }

    public void requestApi(View v)
    {
        DictionaryRequest request = new DictionaryRequest(this,defBox);
        url = dictionaryEntries();
        request.execute(url);
    }

    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = enterWord.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                //TODO: create layout for list of favorite words to go to on user click
                Intent intent = new Intent(SearchScreen.this, Favorites.class);
                startActivity(intent);
                break;

            case R.id.logoutButton:
                logout();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void addToFavorites() {
        Favorites list = new Favorites();
        favoriteWord = enterWord.getText().toString();
        definition = defBox.getText().toString();

        if(!favoriteWord.isEmpty()) {
            String userID = mAuth.getCurrentUser().getUid();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = db.getReference().child("Users").child(userID).child("Favorites");
            //final DatabaseReference myRef2 = db.getReference().child("Users").child(userID);
            myRef.push().setValue(favoriteWord.substring(0,1).toUpperCase() + favoriteWord.substring(1).toLowerCase());
            Toast.makeText(this,"'" + favoriteWord.substring(0,1).toUpperCase() + favoriteWord.substring(1).toLowerCase() + "' has been added to favorites", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this,"Please enter a word", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear()
    {
        String wordToBeCleared;
        wordToBeCleared = enterWord.getText().toString();

        if(!wordToBeCleared.isEmpty())
        {
            enterWord.setText("");
            defBox.setText("");
            Toast.makeText(this, "Cleared",Toast.LENGTH_SHORT).show();
        }
    }
}
