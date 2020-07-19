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

public class SearchScreen extends AppCompatActivity {

    private Button add_favorites;
    private String url;
    public TextView defBox;
    public EditText enterWord;
    private String favoriteWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        defBox = (TextView) findViewById(R.id.definitionBox);
        enterWord = (EditText) findViewById(R.id.search_bar);

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

        if(!favoriteWord.isEmpty()) {
            list.addWord(favoriteWord);
            Toast.makeText(this,"'" +favoriteWord + "'" + " added to Favorites",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Please enter a word", Toast.LENGTH_SHORT).show();
        }
    }
}
