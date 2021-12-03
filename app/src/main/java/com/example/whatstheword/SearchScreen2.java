package com.example.whatstheword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchScreen2 extends AppCompatActivity {

    private Button homeButton;
    private String url;
    public TextView defBox;
    public EditText enterWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen_2);
        getSupportActionBar().setTitle("Lookup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        defBox = (TextView) findViewById(R.id.definitionBox2);
        enterWord = (EditText) findViewById(R.id.search_bar2);
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

    private void exit()
    {
        Intent intent = new Intent(SearchScreen2.this,MainActivity.class);
        startActivity(intent);
    }
}
