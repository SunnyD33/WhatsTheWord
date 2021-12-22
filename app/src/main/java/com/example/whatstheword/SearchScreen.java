package com.example.whatstheword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SearchScreen extends AppCompatActivity {

    private Button clearButton;
    private String url;
    public TextView defBox;
    public EditText enterWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lookup");

        defBox = (TextView) findViewById(R.id.definitionBox);
        enterWord = (EditText) findViewById(R.id.search_bar);
        clearButton = findViewById(R.id.search1ClearButton);

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
            public void onClick(View v) {
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
        DictionaryApi dictionaryApi = new DictionaryApi();
        url = dictionaryApi.dictionaryEntries(enterWord.getText().toString());
        request.execute(url);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SearchScreen.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            startActivity(new Intent(SearchScreen.this,MainActivity.class));
            finish();
            return true;
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
}
