package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SearchScreen2 extends AppCompatActivity {

    private Button clearButton;
    private String url;
    public TextView defBox;
    public EditText enterWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen_2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quick Lookup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        defBox = (TextView) findViewById(R.id.definitionBox2);
        enterWord = (EditText) findViewById(R.id.search_bar2);
        clearButton = findViewById(R.id.search2ClearButton);

        enterWord.requestFocus();
        enterWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    requestApi(textView);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(),0);
                    enterWord.clearFocus();
                    return true;
                }

                return false;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterWord.setText("");
                enterWord.requestFocus();
                defBox.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(enterWord,InputMethodManager.SHOW_IMPLICIT);
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
    public void onBackPressed() {
        Intent intent = new Intent(SearchScreen2.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SearchScreen2.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
